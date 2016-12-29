/**
 * This jQuery plugin displays pagination links inside the selected elements.
 *
 * @author Gabriel Birke (birke *at* d-scribe *dot* de)
 * @version 1.2
 * @param {int} maxentries Number of entries to paginate
 * @param {Object} opts Several options (see README for documentation)
 * @return {Object} jQuery Object
 */
jQuery.fn.pagination = function(maxentries, opts){
	var firstText = "首页";
	var lastText = "尾页";
	var prevText = "上一页";
	var nextText = "下一页";
	var beforeGotoText = "到第";
	var gotoText = "跳转";
	var tipsText = "提示";
	var message1Text = "请输入数字";
	var message2Text = "此页码不存在！";
	var totalText = "共";
	var pagesText = "页";
	var recordsText = "条记录";
	var perpageText = "每页";
	var unitText = "条";
	var okButtonText = "";
	var cancelButtonText="";
	
	if(typeof message != "undefined"){
		firstText = message.pagination.firstPageText;
		lastText = message.pagination.lastPageText;
		prevText = message.pagination.prevPageText;
		nextText = message.pagination.nextPageText;
		beforeGotoText = message.pagination.beforeGotoText;
		gotoText = message.pagination.gotoText;
		tipsText = message.pagination.tips;
		message1Text = message.pagination.numberTips;
		message2Text = message.pagination.invalidNumberTips;
		totalText = message.pagination.totalText;
		pagesText = message.pagination.pagesText;
		recordsText = message.pagination.recordsText;
		perpageText = message.pagination.perpageText;
		unitText = message.pagination.unitText;
		okButtonText = message.defaults.okButton;
		cancelButtonText = message.defaults.cancelButton;
	}
	
	opts = jQuery.extend({
		page_size:20,//每页显示的条目数
		page_index:0,//当前选中的页面,默认是0，表示第1页
		num_display_entries:4,//连续分页主体部分显示的分页条目数
		num_edge_entries:2,//两侧显示的首尾分页的条目数
		link_to : "javascript:void(0);",
		first_text : firstText,
		last_text : lastText,
		prev_text : prevText,
		next_text : nextText,
		before_goto_text : beforeGotoText,
		goto_text: gotoText,
		tips_text : tipsText,
		message1_text : message1Text,
		message2_text : message2Text,
		total_text : totalText,
		pages_text : pagesText,
		records_text : recordsText,
		perpage_text : perpageText,
		unit_text : unitText,
		ellipse_text:"...",
		first_show_always:true,
		last_show_always:true,
		prev_show_always:true,
		next_show_always:true,
		goto_show_always:true,
		stat_show_always:false,
		pagesize_show_always:false,
		callback:function(){return false;}
	},opts||{});
	
	return this.each(function() {
		/**
		 * Calculate the maximum number of pages
		 */
		function numPages() {
			return Math.ceil(maxentries/opts.page_size);
		}
		
		/**
		 * Calculate start and end point of pagination links depending on 
		 * page_index and num_display_entries.
		 * @return {Array}
		 */
		function getInterval()  {
			var ne_half = Math.ceil(opts.num_display_entries/2);
			var np = numPages();
			var upper_limit = np-opts.num_display_entries;
			var start = page_index>ne_half?Math.max(Math.min(page_index-ne_half, upper_limit), 0):0;
			var end = page_index>ne_half?Math.min(page_index+ne_half, np):Math.min(opts.num_display_entries, np);
			return [start,end];
		}
		
		/**
		 * This is the event handling function for the pagination links. 
		 * @param {int} page_id The new page number
		 */
		function pageSelected(page_id, page_size, evt){
			page_index = page_id;
			drawLinks();
			var continuePropagation = opts.callback(page_id, page_size, panel);
			if (!continuePropagation) {
				if (evt.stopPropagation) {
					evt.stopPropagation();
				}
				else {
					evt.cancelBubble = true;
				}
			}
			return continuePropagation;
		}
		
		/**
		 * This function inserts the pagination links into the container element
		 */
		function drawLinks() {
			panel.empty();
			var interval = getInterval();
			var np = numPages();
			
			// This helper function returns a handler function that calls pageSelected with the right page_id
			var getClickHandler = function(page_id, page_size) {
				return function(evt){ 
					return pageSelected(page_id, page_size, evt);
				}
			}
			
			// Helper function for generating a single link (or a span tag if it's the current page)
			var appendItem = function(page_id, appendopts){
				page_id = page_id<0?0:(page_id<np?page_id:np-1); // Normalize page id to sane value
				appendopts = jQuery.extend({text:page_id+1, classes:""}, appendopts||{});
				if(page_id == page_index){
					var lnk = jQuery("<span class='current'>"+(appendopts.text)+"</span>");
				}
				else
				{
					var lnk = jQuery("<a>"+(appendopts.text)+"</a>")
						.bind("click", getClickHandler(page_id, opts.page_size))
						.attr('href', opts.link_to.replace(/__id__/,page_id));
				}
				if(appendopts.classes){lnk.addClass(appendopts.classes);}
				panel.append(lnk);
			}
			
			// Generate "First"-Link
			if(opts.first_text && (page_index > 0 || opts.prev_show_always)){
				appendItem(0,{text:opts.first_text, classes:"first"});
			}
			
			// Generate "Previous"-Link
			if(opts.prev_text && (page_index > 0 || opts.prev_show_always)){
				appendItem(page_index-1,{text:opts.prev_text, classes:"prev"});
			}
			
			// Generate starting points
			if (interval[0] > 0 && opts.num_edge_entries > 0)
			{
				var end = Math.min(opts.num_edge_entries, interval[0]);
				for(var i=0; i<end; i++) {
					appendItem(i);
				}
				if(opts.num_edge_entries < interval[0] && opts.ellipse_text)
				{
					jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
				}
			}
			
			// Generate interval links
			for(var i=interval[0]; i<interval[1]; i++) {
				appendItem(i);
			}
			
			// Generate ending points
			if (interval[1] < np && opts.num_edge_entries > 0)
			{
				if(np-opts.num_edge_entries > interval[1]&& opts.ellipse_text)
				{
					jQuery("<span>"+opts.ellipse_text+"</span>").appendTo(panel);
				}
				var begin = Math.max(np-opts.num_edge_entries, interval[1]);
				for(var i=begin; i<np; i++) {
					appendItem(i);
				}
				
			}
			
			// Generate "Next"-Link
			if(opts.next_text && (page_index < np-1 || opts.next_show_always)){
				appendItem(page_index+1,{text:opts.next_text, classes:"next"});
			}
			
			// Generate "Last"-Link
			if(opts.last_text && (page_index < np-1 || opts.last_show_always)){
				appendItem(np,{text:opts.last_text, classes:"last"});
			}
			
			// Generate "Goto"-Link
			if(opts.goto_text && (page_index < np-1 || opts.goto_show_always)){
				var input = jQuery('<span>'+opts.before_goto_text+'</span><input type="text" class="inputText" maxLength="6" /><span>' + opts.pages_text + '</span>');
				var btn = jQuery('<input type="button" style="width:40px;" value="'+opts.goto_text+'" class="goto" />')
					.bind('click', gotoPageIndex);
					
				input.on("keydown", onKeywords);  
				
				panel.append(input).append(btn);
			}
			
			//Generate pagesize select
			if(opts.pagesize_show_always){
				var input = jQuery('<span>' + opts.perpage_text + '</span>');
				var btn = jQuery('<select><option value="10">10' + opts.unit_text + '</option><option value="20">20' + opts.unit_text + '</option><option value="50">50' + opts.unit_text + '</option><option value="100">100' + opts.unit_text + '</option>')
				.val(opts.page_size)
				.bind('change', function(evt){
						pageSelected(0, $(this).val(),evt);							
					});
				panel.append(input).append(btn);
			}
			
			//Generate 共几页  几条记录
			if(opts.stat_show_always){
				jQuery("<span>"+opts.total_text + np + opts.pages_text + " "+maxentries+ opts.records_text +"</span>").appendTo(panel);
			}
		}
		
		function onKeywords(e){
			if(e.keyCode == 13){
				gotoPageIndex(e);
			} 
		} 
		
		function gotoPageIndex(evt){
			var gotoPage = $(".inputText", ".pagination").val();
			if(gotoPage==null || gotoPage==""){
				gotoPage=1;
			}
			
			//输入非数字不响应
			if (/[^\d]/.test(gotoPage)){
				$(".inputText", ".pagination").off("keydown", onKeywords); 
				dynamicConfirm(opts.message1_text, opts.tips_text, function(){
					$(".inputText", ".pagination").on("keydown", onKeywords); 
				}, "", okButtonText, cancelButtonText , null, "btn-reset NoBtn")
				return false;
			}else{
				//超过了最大页数,显示最后一页
				$(".inputText", ".pagination").off("keydown", onKeywords); 
				if(gotoPage> numPages() || gotoPage < 1){
					dynamicConfirm(opts.message2_text, opts.tips_text, function(){
						$(".inputText", ".pagination").on("keydown", onKeywords); 
					}, "", okButtonText, cancelButtonText , null, "btn-reset NoBtn")
					return false;
				}
				pageSelected(gotoPage-1, opts.page_size, evt);							
			}
		}
		
		// Extract page_index from options
		var page_index = opts.page_index;
		
		// Create a sane value for maxentries and page_size
		maxentries = (!maxentries || maxentries < 0)?1:maxentries;
		opts.page_size = (!opts.page_size || opts.page_size < 0)?1:opts.page_size;
		
		// Store DOM element for easy access from all inner functions
		var panel = jQuery(this);
		
		// Attach control functions to the DOM element 
		this.selectPage = function(page_id){ pageSelected(page_id, opts.page_size);}
		this.prevPage = function(){ 
			if (page_index > 0) {
				pageSelected(page_index - 1, opts.page_size);
				return true;
			}
			else {
				return false;
			}
		}
		this.nextPage = function(){ 
			if(page_index < numPages()-1) {
				pageSelected(page_index+1, opts.page_size);
				return true;
			}
			else {
				return false;
			}
		}
		
		// When all initialisation is done, draw the links
		drawLinks();
		
        // call callback function
        //opts.callback(page_index, this);
	});
}