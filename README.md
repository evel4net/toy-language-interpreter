# Toy Language Interpreter
*Note: Project in progress*

A toy language interpreter implemented in **Java** for the **Advanced Programming Methods** course at *Babeș-Bolyai University* during the first semester of the second year (2025-2026).
The graphical user interface (GUI) is developed using the **JavaFX SDK**.

## Applied Concepts
- Object-Oriented Programming (OOP) fundamentals
- Model-View-Controller (MVC) architecture pattern
- Java generics
- I/O stream handling
- Functional programming
- Memory management (Garbage Collector)
- Concurrency
- Type checking

## Program Examples
```
int a;
a=2+3*5;
int b;
b=a-4/2+7;
Print(b);
```
```
bool a;
int v;
a=true;
If (a) then (
    v=2;
) else (
    v=3;
);
Print(v);

```
```
Ref int v;
new(v, 100);
Ref Ref int a;
new(a, v);
Ref Ref Ref int b;
new(b, a);
new(v, 200);
new(a, v);
new(b, a);
Print(ReadHeap(ReadHeap(ReadHeap(b))));
```
```
int v;
Ref int a;
v=10;
new(a, 22);
Fork(
    WriteHeap(a, 30);
    v=32;
    Print(ReadHeap(a));
    Print(v);
);
Print(v);
Print(ReadHeap(a));
```
