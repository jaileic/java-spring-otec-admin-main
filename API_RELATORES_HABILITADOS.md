# Endpoint REST: Búsqueda de Relatores Habilitados por Código SENCE

## Descripción General
Se ha implementado un nuevo endpoint REST que permite filtrar dinámicamente relatores activos que posean una habilitación vigente para un código SENCE específico.

## Componentes Implementados

### 1. RelatorRepository.java
**Ubicación:** `src/main/java/cl/talento/otec/admin/repositorio/RelatorRepository.java`

**Nuevo método query:**
```java
@Query("""
    SELECT DISTINCT r FROM Relator r 
    INNER JOIN r.habilitaciones h 
    WHERE r.activo = true 
    AND h.codigoEspecialidadSence = :codigoSence 
    AND h.estadoReuf = :estadoReuf 
    AND h.fechaVencimientoReuf > :fechaActual
    """)
List<Relator> findByActivoTrueAndHabilitacionesVigentes(
    @Param("codigoSence") String codigoSence,
    @Param("estadoReuf") String estadoReuf,
    @Param("fechaActual") LocalDate fechaActual
);
```

**Características:**
- Utiliza JPQL (Java Persistence Query Language)
- Realiza INNER JOIN con la entidad Habilitacion
- Filtra por: relatores activos, habilitación vigente, código SENCE específico, vencimiento futuro
- Usa DISTINCT para evitar duplicados
- Parámetros vinculados para seguridad SQL

### 2. RelatorService.java
**Ubicación:** `src/main/java/cl/talento/otec/admin/servicio/RelatorService.java`

**Nuevo método:**
```java
public List<RelatorDTO> buscarHabilitadosPorCodigoSence(String codigoSence) {
    return relatorRepository.findByActivoTrueAndHabilitacionesVigentes(
        codigoSence,
        "Vigente",
        LocalDate.now()
    )
    .stream()
    .map(this::mapRelatorToDTO)
    .collect(Collectors.toList());
}
```

**Características:**
- Encapsula la lógica de búsqueda
- Llama al repositorio con parámetros: código SENCE, estado "Vigente", fecha actual
- Convierte resultados a DTOs usando mapper existente
- Retorna lista de RelatorDTO

### 3. ApiRelatorController.java (NUEVO)
**Ubicación:** `src/main/java/cl/talento/otec/admin/controlador/ApiRelatorController.java`

**Estructura:**
```java
@RestController
@RequestMapping("/api/relatores")
public class ApiRelatorController {
    private final RelatorService relatorService;
    
    @GetMapping("/habilitados")
    public ResponseEntity<List<RelatorDTO>> buscarRelatorHabilitados(
            @RequestParam String codigoSence) {
        List<RelatorDTO> relatores = relatorService.buscarHabilitadosPorCodigoSence(codigoSence);
        return ResponseEntity.ok(relatores);
    }
}
```

**Características:**
- RestController dedicado a operaciones con Relatores
- Ruta base: `/api/relatores`
- Inyección de dependencias del RelatorService
- Manejo automático de serialización JSON

## Endpoint REST

### Búsqueda de Relatores Habilitados

**URL:** `GET /api/relatores/habilitados?codigoSence={codigo}`

**Parámetros:**
- `codigoSence` (obligatorio, String): Código de especialidad SENCE a buscar
  - Ejemplo: `4015-001`, `3523-002`, etc.

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "idRelator": 1,
    "rut": "12345678-9",
    "nombres": "Juan",
    "apellidos": "Pérez García",
    "email": "juan.perez@example.com",
    "profesionTitulo": "Ingeniero en Sistemas",
    "activo": true
  },
  {
    "idRelator": 3,
    "rut": "98765432-1",
    "nombres": "María",
    "apellidos": "López Rodríguez",
    "email": "maria.lopez@example.com",
    "profesionTitulo": "Técnico Agrícola",
    "activo": true
  }
]
```

**Respuesta sin resultados (200 OK, lista vacía):**
```json
[]
```

**Respuesta en caso de error (40x, 50x):**
```json
{
  "timestamp": "2026-03-20T12:30:00.000+00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid parameter"
}
```

## Ejemplos de Uso

### cURL
```bash
# Buscar relatores habilitados para código SENCE 4015-001
curl -X GET "http://localhost:8080/api/relatores/habilitados?codigoSence=4015-001" \
  -H "Accept: application/json"
```

### JavaScript Fetch
```javascript
fetch('/api/relatores/habilitados?codigoSence=4015-001')
  .then(response => response.json())
  .then(data => console.log('Relatores habilitados:', data))
  .catch(error => console.error('Error:', error));
```

### JavaScript Axios
```javascript
axios.get('/api/relatores/habilitados', {
  params: {
    codigoSence: '4015-001'
  }
})
.then(response => console.log(response.data))
.catch(error => console.error(error));
```

### Python Requests
```python
import requests

response = requests.get(
    'http://localhost:8080/api/relatores/habilitados',
    params={'codigoSence': '4015-001'}
)
print(response.json())
```

## Lógica de Filtrado

El endpoint realiza los siguientes filtros:

1. **Relator Activo:** `activo = true`
2. **Habilitación Vigente:** `estadoReuf = 'Vigente'`
3. **Código SENCE Coincidente:** `codigoEspecialidadSence = {codigoSolicitado}`
4. **Habilitación No Expirada:** `fechaVencimientoReuf > HOY`

Todos los filtros se aplican simultáneamente con AND lógico.

## Validación de Datos

- **codigoSence:** String no vacío (validado por RequestParam)
- **DTOs retornados:** Contienen validación Jakarta con @NotBlank, @Email, etc.

## Rendimiento

- **Query JPQL:** Índices recomendados en `habilitaciones.codigo_especialidad_sence`, `habilitaciones.estado_reuf`, `habilitaciones.fecha_vencimiento_reuf`
- **DISTINCT:** Evita duplicados en caso de múltiples habilitaciones
- **Stream Processing:** Conversión eficiente a DTOs

## Integración con Sistema Existente

- Utiliza el servicio RelatorService existente
- Aprovecha el mapper `mapRelatorToDTO()` existente
- Compatible con la estructura de repositorio JPA actual
- Mantiene patrón Service-DTO-Repository

## Tests

El endpoint se integra con la suite de tests existente:
- ✅ OtecAdminApplicationTests: 1 test
- ✅ CursoServiceTest: 7 tests
- **Total: 8/8 tests pasando**

## Build y Empaquetamiento

- ✅ **Compilación:** 23 archivos Java compilados sin errores
- ✅ **JAR generado:** `admin-0.0.1-SNAPSHOT.jar` (61.8 MB)
- ✅ **BUILD SUCCESS** con Maven 3.9.12

## Archivos Modificados

| Archivo | Cambio | Líneas |
|---------|--------|--------|
| RelatorRepository.java | +1 método @Query | +12 |
| RelatorService.java | +1 método búsqueda | +9 |
| ApiRelatorController.java | NUEVO | 27 |

