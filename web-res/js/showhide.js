function focus(id)    
{
    var z=document.getElementById(id); 
    try {
        z.focus();
        z.select();
    } catch(e) {
    }
}

function hide(id)
{
  $('#'+id).hide('fast');
}


function show(id) 
{
  $('#'+id).show('fast');
}

function show(id, focusId) 
{
  $('#'+id).show('fast');
  focus(focusId);
}




