$(document).ready(function(){
var APIURL = 'http://localhost:9000/entreprises';

// Load Data from API
var  loadData = function (){
  $.getJSON(APIURL, function(dt){
    var i;
    var out = "";
    for(i = 0; i < dt.length; i++) {
      out +=
      '<tr><td class="hidden"><span>' + dt[i].id +'</span></td>'+
      '<td><span class="to-hide name">' + dt[i].name +'</span>'+
      '<input type="text" class="form-control to-show name-input" value="' + dt[i].name + '" placeholder="Name"></td>'+
      '<td><span class="to-hide capital">' + dt[i].capital  + '</span>'+
      '<input type="number" class="form-control to-show capital-input" value="' + dt[i].capital  + '" placeholder="Capital"></td>'+
      '<td><span class="to-hide description">' + dt[i].description  + '</span>'+
      '<input type="text" class="form-control to-show description-input" value="' + dt[i].description  + '" placeholder="Description"></td>'+
      '<td><span class="glyphicon glyphicon-ok to-show confirm" aria-hidden="true">&nbsp</span>'+
      '<span class="glyphicon glyphicon-edit edit to-hide" aria-hidden="true">&nbsp</span>'+
      '<span class="glyphicon glyphicon-trash data remove" aria-hidden="true"></span></td></tr>';
    }
    document.getElementById("table-body").innerHTML = out;
  });
};

// Add
  $(document).on('click','#btn-add',function(){

    /* initialization */
    var name = $('#name').val();
    var capital = $('#capital').val();
    var description = $('#description').val();
    var data = {};
    data["name"]=name;
    data["capital"]=capital;
    data["description"]=description;

    // construct an HTTP request
    var xhr = new XMLHttpRequest();
    xhr.open("POST", APIURL, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xhr.send(JSON.stringify(data));
    xhr.onloadend = function () {
      $('#name').val('');
      $('#capital').val('');
      $('#description').val('');
      loadData();
    };
  });

  // Reset Form
  $(document).on('click','#btn-reset',function(){
    $('#name').val('');
    $('#capital').val('');
    $('#description').val('');
  });

  // Remove
  $(document).on('click','.remove',function(){

    // Getting the id
    var id =$(this).parent('td').parent('tr').children().first().first().text();

    // AJAX HTTP request
    $.ajax({
      type: 'DELETE',
      url: APIURL+'/'+id,
      success: function(msg){
        alert("Data Deleted: ");
      }
    });
    $(this).parent('td').parent('tr').remove();
  });

  // Edit
  $(document).on('click','.edit',function(){
    $(this).parent('td').parent('tr').find('.to-hide').slideUp(300);
    $(this).parent('td').parent('tr').find('.to-show').slideDown(300);
  });

  // Edit Confirm
  $(document).on('click','.confirm',function(){

    // Getting values
    var parentTarget = $(this).parent('td').parent('tr');
    var name = parentTarget.find('.name-input').val();
    var capital = parentTarget.find('.capital-input').val();
    var description = parentTarget.find('.description-input').val();
    var id =$(this).parent('td').parent('tr').children().first().first().text();
    var data = {};
    data["name"]=name;
    data["capital"]=capital;
    data["description"]=description;

    // construct an HTTP request
    var xhr = new XMLHttpRequest();
    xhr.open("PUT", APIURL+"/"+id, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xhr.send(JSON.stringify(data));
    xhr.onloadend = function () {
      parentTarget.find('.name').text(name);
      parentTarget.find('.capital').text(capital);
      parentTarget.find('.description').text(description);
      parentTarget.find('.to-show').slideUp(500);
      parentTarget.find('.to-hide').slideDown(500);
    };
  });
loadData();
});
