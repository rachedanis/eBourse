$(document).ready(function(){
var APIURL = 'http://localhost:9000/marketvalues';
var GET_VALUES_APIURL = 'http://localhost:9000/values';


// Load Data from API
var  loadData = function (){
  $.getJSON(APIURL, function(dt){
    var i;
    var out = "";
    for(i = 0; i < dt.length; i++) {
      var date =new Date(dt[i].date);
      out +=
      '<tr><td class="hidden"><span>' + dt[i].id +'</span></td>'+
      '<td><span class="nature">' + dt[i].value.nature +'</span>'+
      '<td><span class="isin">' + dt[i].value.isin  + '</span>'+
      '<td><span class="to-hide cours">' + dt[i].cours  + '</span>'+
      '<input type="number" class="form-control to-show cours-input" value="' + dt[i].cours  + '" placeholder="Cours"></td>'+
      '<td><span class="date">' + date + '</span>'+
      '<td><span class="glyphicon glyphicon-ok to-show confirm" aria-hidden="true">&nbsp</span>'+
      '<span class="glyphicon glyphicon-edit edit to-hide" aria-hidden="true">&nbsp</span>'+
      '<span class="glyphicon glyphicon-trash data remove" aria-hidden="true"></span></td></tr>';
    }
    document.getElementById("table-body").innerHTML = out;
  });
};

var  loadValues = function (id){
  $.getJSON(GET_VALUES_APIURL, function(dt){
    var i;
    var out = "";
    for(i = 0; i < dt.length; i++) {
      out +=
      '<option id="visin" value="'+dt[i].isin+'">' + dt[i].isin +'</option>';
        }
    document.getElementById("values").innerHTML = out;
  });
};

// Add
  $(document).on('click','#btn-add',function(){

    /* initialization */
    var isin = $('#values').val();
    var cours = $('#cours').val();
    var data = {};
    data.value ={};
    data.value["isin"]=isin;
    data["cours"]=cours;

    // construct an HTTP request
    var xhr = new XMLHttpRequest();
    xhr.open("POST", APIURL, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xhr.send(JSON.stringify(data));
    xhr.onloadend = function () {
      $('#cours').val('');
      loadData();
    };
  });

  // Reset Form
  $(document).on('click','#btn-reset',function(){
    $('#cours').val('');
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
    var nature = parentTarget.find('.nature-input').val();
    var isin = parentTarget.find('.isin-input').val();
    var cours = parentTarget.find('.cours-input').val();
    var id =$(this).parent('td').parent('tr').children().first().first().text();
    var data = {};
    data["cours"]=cours;

    // construct an HTTP request
    var xhr = new XMLHttpRequest();
    xhr.open("PUT", APIURL+"/"+id, true);
    xhr.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xhr.send(JSON.stringify(data));
    xhr.onloadend = function () {
      parentTarget.find('.nature').text(nature);
      parentTarget.find('.isin').text(isin);
      parentTarget.find('.cours').text(cours);
      parentTarget.find('.to-show').slideUp(500);
      parentTarget.find('.to-hide').slideDown(500);
    };
  });

  loadValues();
  loadData();

});
