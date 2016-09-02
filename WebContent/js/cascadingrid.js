/**
 * CASCADING GRID USING MASONRY
 */

var grid = document.querySelector('.grid');
var msnry = new Masonry(grid, {
	// options
	itemSelector : 'div[class^="col-"]',
	columnWidth : 'div[class^="col-"]',
	percentPosition : true
});
var imgLoad = imagesLoaded(grid);

imgLoad.on('progress', function(instance, image) {
	msnry.layout('layout');
});