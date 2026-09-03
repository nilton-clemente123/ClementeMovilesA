## REGISTRO DE PRODUCTOS

### Nombre: Nilton Javier


### Descripcion

Este es un trabajo academico que consiste en registrar productos introduciendo el nombre, su precio y su cantidad

### Capturas del emulador


#### ¿Qué pasaría si declaras las variables de los campos SIN remember?

Si hubiera declarado las variables de los campos sin remember, sus valores se reiniciaran cada vez que ocurra una recomposición de la interfaz, haciendo que se pierda la información ingresada por el usuario

Al hacer la prueba reemplazando: 

```kotlin
var nombre by remember { mutableStateOf("") }
```

por 

```kotlin
var nombre = ""
```

Al probar el programa en el emulador, mientras se va escribiendo cada letra para el nombre, dicho componente se va actualizando, entonces al no colocar el termino remember aparentemente parece que no se permite ingresar texto cuando en realidad el valor se esta reiniciando de forma indefinida.
