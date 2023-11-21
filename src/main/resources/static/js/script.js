//Despliegue del menu principal
let listElements = document.querySelectorAll('.lista-item, .lista-item-2');

listElements.forEach(listElement => {
    listElement.addEventListener('click', () => {
        listElement.classList.toggle('arrow');
        let menu = listElement.nextElementSibling;
        menu.classList.toggle('active');
    });
});

//ejecutar funcion con click
document.getElementById("btn-open").addEventListener("click", open_close_menu);

//creacion de variables
var side_menu = document.getElementById("menu-principal");
var btn_open = document.getElementById("btn-open");
var body = document.getElementById("body");
var lista = document.getElementById("lista-close");
var contenido = document.getElementById("contenedor-move");

//evento para mostrar y ocultar menu
function open_close_menu(){
    body.classList.toggle("body-move");
    side_menu.classList.toggle("menu-principal-move");
    lista.classList.toggle("lista-close");
    contenido.classList.toggle("contenedor-move");
    body.classList.toggle("moved");
}