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

_B@main dd ?
_A@main dd ?

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
start:
FINIT 
MOV ecx, 100
MOV _A@main, ecx
MOV ecx, 5
MOV _B@main, ecx
MOV eax, _A@main
IMUL eax, _B@main
CALL ControlarRangoEntero
MOV ecx, 500
CMP ecx, 0
JNE _ERROR_DIV_ZERO1
invoke StdOut, addr ERROR_DIVISION_POR_CERO
invoke ExitProcess, 0
_ERROR_DIV_ZERO1:
CDQ
MOV @aux0, 500
IDIV @aux0
MOV _A@main, eax
invoke printf, cfm$("%d\n"), _A@main

END start