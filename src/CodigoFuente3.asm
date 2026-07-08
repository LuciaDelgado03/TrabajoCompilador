.386                  ; Especifica la arquitectura del procesador (386 o superior)
.model flat, stdcall  ; Modelo de memoria y convenciones de llamada
option casemap:none   ; Sensibilidad a mayúsculas/minúsculas

include\masm32\include\masm32rt.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib

includelib \masm32\lib\masm32.lib

dll_dllcrt0 PROTO C
printf PROTO C : VARARG

.data                 ; Segmento de datos
@aux0 DD ?

@varAuxMax dd ?
@varAuxMin dd ?
@varAuxMaxDouble DQ ?
@varAuxMinDouble DQ ?
@varAuxRangoDouble DQ ?
@varDivCero dd 00h 
aux_mem_2bytes dw ? ; Variable de 2 bytes no inicializada
ERROR_DIVISION_POR_CERO DB "ERROR: Se intento dividir por cero", 10, 0

ERROR_RANGO DB "ERROR: Fuera de rango", 10, 0

_SUMAR@main dd ?
_A@main@SUMAR@DIVIDIR DQ ?
_DIVIDIR@main@SUMAR DQ ?
_X@main@SUMAR dd ?
_RET@main@SUMAR@DIVIDIR DQ ?
_RET@main@SUMAR dd ?
DOUBLE0@0 DQ 0.0

.code                 ; Segmento de código

ControlarRangoEntero:
JO OverflowEntero
JC OverflowEntero
RET
ControlarRangoEnterito:
; Comparar con el maximo
CMP edx, @varAuxMax
JG OverflowEntero
; Comparar con el minimo
CMP edx, @varAuxMin
JL OverflowEntero
RET

OverflowEntero:
invoke StdOut, addr ERROR_RANGO
invoke ExitProcess, 0

ControlarRangoDouble:
; Comparar con el limite maximo
FLD @varAuxRangoDouble
FLD @varAuxMaxDouble
FCOM
FSTSW aux_mem_2bytes
MOV AX, aux_mem_2bytes
SAHF
JB OverflowDouble
; Comparar con el limite minimo
FLD @varAuxRangoDouble
FLD @varAuxMinDouble
FCOM
FSTSW aux_mem_2bytes
MOV AX, aux_mem_2bytes
SAHF
JA OverflowDouble
; Retornar si está dentro del rango
FINIT
FLD @varAuxRangoDouble
RET

OverflowDouble: 
invoke StdOut, addr ERROR_RANGO
invoke ExitProcess, 0
SUMAR@main:
push ebp ; Save the old base pointer value
mov ebp, esp ; Set the new base pointer value
sub esp, 4 ; Make room for one 4-byte local variable
push edi ; Save the values of registers that the function
push esi ; will modify. This function uses EDI and ESI)
MOV ecx, 1
ADD ecx, 1
MOV _X@main@SUMAR, ecx
MOV ecx, _X@main@SUMAR
MOV _RET@main@SUMAR, ecx
pop esi ; Recover register values
pop edi 
mov esp, ebp ; Deallocate local variables 
pop ebp ; Restore the caller's base pointer value 
RET
MOV _RET@main@SUMAR, 0
pop esi ; Recover register values
pop edi 
mov esp, ebp ; Deallocate local variables 
pop ebp ; Restore the caller's base pointer value 
RET
DIVIDIR@main@SUMAR:
push ebp ; Save the old base pointer value
mov ebp, esp ; Set the new base pointer value
sub esp, 4 ; Make room for one 4-byte local variable
push edi ; Save the values of registers that the function
push esi ; will modify. This function uses EDI and ESI)
MOV ebx, 2
CMP ebx, 0
JNE _ERROR_DIV_ZERO1
invoke StdOut, addr ERROR_DIVISION_POR_CERO
invoke ExitProcess, 0
_ERROR_DIV_ZERO1:
MOV eax, _X@main@SUMAR
CDQ
MOV @aux0, 2
IDIV @aux0
MOV _X@main@SUMAR, eax
FLD _A@main@SUMAR@DIVIDIR
FSTP _RET@main@SUMAR@DIVIDIR
pop esi ; Recover register values
pop edi 
mov esp, ebp ; Deallocate local variables 
pop ebp ; Restore the caller's base pointer value 
RET
FLD DOUBLE0@0
FSTP _RET@main@SUMAR@DIVIDIR
pop esi ; Recover register values
pop edi 
mov esp, ebp ; Deallocate local variables 
pop ebp ; Restore the caller's base pointer value 
RET
start:
FINIT 

END start