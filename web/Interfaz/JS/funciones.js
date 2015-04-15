function pasuser(form) {
if (form.user.value=="vaalsoft") { 
if (form.pass.value=="vaalsoft") {              
location="interfaz02_bienvenido.xhtml" 
} else {
alert("Contraseña Invalida")
}
} else {  alert("Usuario Invalido")
}
}