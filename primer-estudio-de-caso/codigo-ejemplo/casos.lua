local nombre = "Pablo"
print("Hola " .. nombre .. "!")

local casos = {
  'print("Hola " .. "mundo")',
  'print("Hola " ..)',
  'print("Hola)',
  'print("Hola " .. x)',
}
for _, c in ipairs(casos) do
  print("> " .. c)
  local f, err = load(c)
  if not f then
    print("  ERROR de sintaxis: " .. err)
  else
    local ok, e = pcall(f)
    if not ok then print("  ERROR en ejecucion: " .. e) end
  end
end
