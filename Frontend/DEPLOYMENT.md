# Configuración de Despliegue con Vercel - Simplificada

Este documento explica cómo configurar el despliegue automático del frontend Rosa a Vercel usando el pipeline CI/CD simplificado.

## 🚀 Configuración Inicial de Vercel

### 1. Crear cuenta en Vercel
- Ve a [vercel.com](https://vercel.com) y crea una cuenta
- Conecta tu cuenta de GitHub

### 2. Importar proyecto desde GitHub
1. En tu dashboard de Vercel, haz clic en "New Project"
2. Selecciona tu repositorio de GitHub
3. Configura el directorio raíz: `Rosa-GestionClases-Frontend`
4. Framework: Angular
5. Haz clic en "Deploy"

### 3. Obtener información del proyecto
Después del primer despliegue:
1. Ve a Project Settings
2. Copia el Project ID
3. Ve a Account Settings → General → Your ID (Team/Org ID)

## 🔐 Configuración de Secrets en GitHub

Debes agregar los siguientes secrets en tu repositorio de GitHub:

### Secrets requeridos:
1. **VERCEL_TOKEN**: Token de acceso a Vercel
2. **VERCEL_ORG_ID**: ID de tu organización/equipo en Vercel
3. **VERCEL_PROJECT_ID**: ID del proyecto en Vercel

### Pasos para obtener los secrets:

#### 1. VERCEL_TOKEN
```bash
# Crear token en Vercel
vercel login
# Ve a: https://vercel.com/account/tokens
# Crea un nuevo token con los permisos necesarios
```

#### 2. VERCEL_ORG_ID
```bash
# Obtener desde proyecto local
cat .vercel/project.json
# El campo \"orgId\" es tu VERCEL_ORG_ID
```

#### 3. VERCEL_PROJECT_ID
```bash
# Obtener desde proyecto local
cat .vercel/project.json
# El campo \"projectId\" es tu VERCEL_PROJECT_ID
```

### Agregar secrets en GitHub:
1. Ve a tu repositorio → Settings → Secrets and variables → Actions
2. Haz clic en "New repository secret"
3. Agrega cada secret con su valor correspondiente

## 📋 Configuración del Pipeline

### Archivos importantes:
- `.github/workflows/rosa-frontend-ci-cd.yml` - Pipeline principal
- `Rosa-GestionClases-Frontend/vercel.json` - Configuración de Vercel
- `Rosa-GestionClases-Frontend/package.json` - Scripts de build

### Configuración de build:
```json
{
  "scripts": {
    "build": "ng build --configuration production",
    "build:vercel": "ng build --configuration production --output-path=dist/angular"
  }
}
```

## 🌐 Proceso de Despliegue

### Automático:
1. **Push a rama principal**: El pipeline se ejecuta automáticamente
2. **Pull Request**: Se despliega una preview del cambio
3. **Merge**: Se despliega a producción

### Manual:
```bash
# Desplegar manualmente
vercel --prod

# Desplegar preview
vercel
```

## 🔧 Configuración Avanzada

### Variables de entorno:
```bash
# Agregar variables de entorno en Vercel
vercel env add API_URL production
vercel env add ENVIRONMENT production
```

### Dominios personalizados:
```bash
# Agregar dominio personalizado
vercel domains add example.com
```

### Configuración de headers:
El archivo `vercel.json` incluye headers de seguridad:
- X-Frame-Options
- X-Content-Type-Options
- Referrer-Policy

## 📊 Monitoreo

### Logs de despliegue:
- Ve a tu dashboard de Vercel
- Selecciona el proyecto
- Revisa los logs de deployment

### Analytics:
- Vercel proporciona analytics básico
- Puedes integrar Google Analytics si es necesario

## 🐛 Troubleshooting

### Errores comunes:

1. **Token inválido**:
   ```bash
   vercel login
   # Genera un nuevo token
   ```

2. **Build fallido**:
   ```bash
   # Revisar logs en Vercel dashboard
   # Verificar configuración de build
   ```

3. **Rutas no funcionan**:
   ```json
   // Verificar configuración en vercel.json
   "rewrites": [
     {
       "source": "/(.*)",
       "destination": "/index.html"
     }
   ]
   ```

## 📱 URLs de Despliegue

### Estructura de URLs:
- **Producción**: `https://rosa-frontend-alesky-gym.vercel.app`
- **Preview**: `https://rosa-frontend-alesky-gym-[branch].vercel.app`
- **Desarrollo**: `https://rosa-frontend-alesky-gym-[commit].vercel.app`

### Notificaciones:
- El pipeline comenta en PRs con la URL de preview
- Los logs muestran la URL de producción

---

**Nota**: Asegúrate de que todos los secrets estén configurados correctamente antes de hacer push a las ramas principales.
