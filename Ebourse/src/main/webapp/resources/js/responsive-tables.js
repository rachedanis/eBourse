$(document).ready(function() {
  var switched = false;
  var updateTables = function() {
      switched = true;
      $("table.responsive").each(function(i, element) {
        splitTable($(element));
      });
      return true;
  };

  $(window).load(updateTables);
	function splitTable(original)
	{
		original.wrap("<div class='table-wrapper' />");
		original.wrap("<div class='scrollable' />");

    setCellHeights(original);
	}

  function setCellHeights(original) {
    var tr = original.find('tr'),
        heights = [];
    tr.each(function (index) {
      var self = $(this),
          tx = self.find('th, td');
      tx.each(function () {
        var height = $(this).outerHeight(true);
        heights[index] = heights[index] || 0;
        if (height > heights[index]) heights[index] = height;
      });

    });
  }

});
