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
@aux1 dd ?
@aux0 DQ ?

@varAuxMax dd ?
@varAuxMin dd ?
@varAuxMaxDouble DQ ?
@varAuxMinDouble DQ ?
@varAuxRangoDouble DQ ?
@varDivCero dd 00h 
aux_mem_2bytes dw ? ; Variable de 2 bytes no inicializada
ERROR_DIVISION_POR_CERO DB "ERROR: Se intento dividir por cero", 10, 0

ERROR_RANGO DB "ERROR: Fuera de rango", 10, 0

_J@main dd ?
_Y2@main@TIENEALCANCE dd ?
_CS@main DQ ?
_STRUCT2_CS@main DQ ?
_STRUCT1_BS@main DQ ?
DOUBLE10@0 DQ 10.0
_RET@main@SUMAR dd ?
_STRUCT2_AS@main dd ?
_Z@main@TIENEALCANCE dd ?
_STRUCT1_CS@main DQ ?
_F@main DQ ?
_TIENEALCANCE@main dd ?
_STRUCT2_BS@main DQ ?
DOUBLE2@0 DQ 2.0
DOUBLE2@1 DQ 2.1
DOUBLE6@0 DQ 6.0
_SUMAR@main dd ?
_RET@main@TIENEALCANCE dd ?
_STRUCT1_AS@main dd ?
_CM@main dd ?
_X@main@SUMAR dd ?
_C@main DQ ?
_HEX@main dd ?
_E@main dd ?
_B@main dd ?
DOUBLE5@0 DQ 5.0
DOUBLE5@7 DQ 5.7
_Y@main@SUMAR dd ?
_GT@main dd ?
_AS@main dd ?
_HEX2@main DQ ?
_AM@main dd ?
DOUBLE0@0 DQ 0.0
DOUBLE4@0 DQ 4.0
_D@main DQ ?
_BM@main dd ?
_A@main dd ?
_BS@main DQ ?

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
TIENEALCANCE@main:
push ebp ; Save the old base pointer value
mov ebp, esp ; Set the new base pointer value
sub esp, 4 ; Make room for one 4-byte local variable
push edi ; Save the values of registers that the function
push esi ; will modify. This function uses EDI and ESI)
MOV ebx, _Z@main@TIENEALCANCE
MOV _RET@main@TIENEALCANCE, ebx
pop esi ; Recover register values
pop edi 
mov esp, ebp ; Deallocate local variables 
pop ebp ; Restore the caller's base pointer value 
RET
MOV _RET@main@TIENEALCANCE, 0
pop esi ; Recover register values
pop edi 
mov esp, ebp ; Deallocate local variables 
pop ebp ; Restore the caller's base pointer value 
RET
start:
FINIT 
MOV eax, _B@main
ADD eax, 1
MOV _A@main, eax
MOV eax, 10
MOV _A@main, eax
MOV eax, _A@main
IMUL eax, _B@main
CALL ControlarRangoEntero
MOV @aux1, eax
FILD @aux1
FST @aux0
FLD _D@main
FLD @aux0
FADD
FSTP _D@main
MOV edx, 2
MOV @varAuxMin, -1
MOV @varAuxMax, 3
CALL ControlarRangoEnterito
MOV eax, 2
MOV _J@main, eax
MOV eax, 5
ADD eax, _J@main
MOV edx, eax
MOV @varAuxMin, -1
MOV @varAuxMax, 3
CALL ControlarRangoEnterito
MOV _J@main, eax
MOV eax, _J@main
CMP eax, 4
JGE L34
MOV edx, 0
MOV @varAuxMin, -1
MOV @varAuxMax, 3
CALL ControlarRangoEnterito
MOV eax, 0
MOV _J@main, eax
JMP L34
L34:
FLD DOUBLE2@1
FSTP @varAuxRangoDouble
FLD DOUBLE0@0
FSTP @varAuxMinDouble
FLD DOUBLE5@7
FSTP @varAuxMaxDouble 
CALL ControlarRangoDouble
FSTP _F@main
FLD _F@main
FLD _F@main
FADD
FSTP @varAuxRangoDouble
FLD DOUBLE0@0
FSTP @varAuxMinDouble
FLD DOUBLE5@7
FSTP @varAuxMaxDouble 
CALL ControlarRangoDouble
FSTP _F@main
FLD _F@main
FLD DOUBLE6@0
FCOM
FSTSW aux_mem_2bytes
MOV AX, aux_mem_2bytes
SAHF
JBE L53
FLD DOUBLE0@0
FSTP @varAuxRangoDouble
FLD DOUBLE0@0
FSTP @varAuxMinDouble
FLD DOUBLE5@7
FSTP @varAuxMaxDouble 
CALL ControlarRangoDouble
FSTP _F@main
JMP L57
L53:
FLD DOUBLE4@0
FSTP @varAuxRangoDouble
FLD DOUBLE0@0
FSTP @varAuxMinDouble
FLD DOUBLE5@7
FSTP @varAuxMaxDouble 
CALL ControlarRangoDouble
FSTP _F@main
L57:
MOV eax, _HEX@main
CMP eax, 11
JLE L70
MOV eax, _HEX@main
ADD eax, 2
MOV _HEX@main, eax
JMP L76
L70:
MOV eax, _HEX@main
ADD eax, 2
MOV _HEX@main, eax
L76:
MOV eax, _HEX@main
CMP eax, 12
JLE L102
MOV eax, _HEX@main
ADD eax, 2
MOV _HEX@main, eax
MOV eax, _HEX@main
CMP eax, 10
JLE L99
MOV eax, _HEX@main
ADD eax, 2
MOV _HEX@main, eax
JMP L99
L99:
JMP L102
L102:
FLD _HEX2@main
FLD DOUBLE5@0
FCOM
FSTSW aux_mem_2bytes
MOV AX, aux_mem_2bytes
SAHF
JAE L115
FLD _HEX2@main
FLD DOUBLE2@0
FADD
FSTP _HEX2@main
JMP L121
L115:
FLD _HEX2@main
FLD DOUBLE2@0
FADD
FSTP _HEX2@main
L121:
FLD _HEX2@main
FLD DOUBLE10@0
FCOM
FSTSW aux_mem_2bytes
MOV AX, aux_mem_2bytes
SAHF
JAE L147
FLD _HEX2@main
FLD DOUBLE2@0
FADD
FSTP _HEX2@main
FLD _HEX2@main
FLD DOUBLE10@0
FCOM
FSTSW aux_mem_2bytes
MOV AX, aux_mem_2bytes
SAHF
JAE L144
FLD _HEX2@main
FLD DOUBLE2@0
FADD
FSTP _HEX2@main
JMP L144
L144:
JMP L147
L147:
MOV eax, 1
ADD eax, 4
MOV _AM@main, eax
MOV eax, 2
IMUL eax, 4
CALL ControlarRangoEntero
MOV _BM@main, eax
MOV eax, _AM@main
ADD eax, 5
MOV _CM@main, eax
MOV eax, 4
IMUL eax, 4
CALL ControlarRangoEntero
ADD eax, 4
MOV _GT@main, eax
MOV eax, 3
ADD eax, 456
MOV _GT@main, eax
JMP etiquetaPrueba@
JMP etiquetaPrueba2@
MOV eax, 454
MOV _GT@main, eax
MOV eax, 1
MOV _GT@main, eax
MOV eax, 0
MOV _GT@main, eax
L189:
MOV eax, _HEX@main
ADD eax, 1
MOV _HEX@main, eax
MOV eax, _HEX@main
CMP eax, 10
JGE L202
etiquetaPrueba@:
JMP L189
L202:

END start