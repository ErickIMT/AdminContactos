console.log("Archivo JavaScript Leido");

const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    //Ocultar SideBar
    $(".sidebar").css("display", "none");
    $("#seccion1").css("margin-left", "1%");
    $("#seccion1").css("width", "98%");
    $("footer").css("margin-left", "1%");
  } else {
    //Mostrar SideBar
    $(".sidebar").css("display", "block");
    $("#seccion1").css("margin-left", "20%");
    $("#seccion1").css("width", "78%");
    $("footer").css("margin-left", "20%");
  }
};

const busqueda = () => {
  //console.log("Buscando...");
  let query = $("#search-input").val();

  if (query == "") {
    $(".search-result").hide();
  } else {
    console.log(query);

    //busqueda de resultados en el backend

    let url = `http://localhost:9396/buscar/${query}`;

    fetch(url)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        console.log(data);

        let text = `<div class='list-group'>`;

        data.forEach((contacto) => {
          text += `<a href='/usuario/${contacto.id}/contacto' class='list-group-item list-group-item-action'>${contacto.nombre} ${contacto.apellidos} </a>`;
        });

        text += `</div>`;

        $(".search-result").html(text);
        $(".search-result").show();
      });
  }
};
//Tooltip
$(document).tooltip();

