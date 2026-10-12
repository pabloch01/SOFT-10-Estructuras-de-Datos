#include <stdio.h>
#include <lua.h>
#include <lauxlib.h>

/* Imprime la pila virtual de Lua de tope a base */
static void mostrarPila(lua_State *L, const char *titulo) {
    int n = lua_gettop(L);
    printf("%s (elementos: %d)\n", titulo, n);
    for (int i = n; i >= 1; i--) {
        const char *marca = (i == n) ? "  <- tope" : (i == 1 ? "  <- base" : "");
        printf("  [%d] \"%s\"%s\n", i, lua_tostring(L, i), marca);
    }
    printf("\n");
}

int main(void) {
    lua_State *L = luaL_newstate();

    lua_pushstring(L, "Hola ");   /* token 1: cadena literal   */
    lua_pushstring(L, "Pablo");   /* token 2: variable (valor) */
    lua_pushstring(L, "!");       /* token 3: cadena literal   */
    mostrarPila(L, "Pila antes de lua_concat(L, 3)");

    lua_concat(L, 3);             /* saca 3 elementos, empuja 1 */
    mostrarPila(L, "Pila luego de lua_concat(L, 3)");

    lua_close(L);
    return 0;
}
