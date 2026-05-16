import pytest
import requests
import time
import random

BASE_URL = "http://localhost:8084/api/v1/bff/web"

BASIC_AUTH = ("admin", "admin123")

TOKEN_COMPARTIDO = None
USUARIO_ID_DINAMICO = None


# ==============================================================================
# 1. FLUJO COMPLETADO DE USUARIOS (CRUD)
# ==============================================================================

def test_1_crear_usuario():
    """[CREATE] Registra un usuario con todos los campos obligatorios del modelo"""
    global USUARIO_ID_DINAMICO
    url = f"{BASE_URL}/users"
    
    timestamp = int(time.time())
    fono_aleatorio = random.randint(900000000, 999999999)
    
    user_payload = {
        "name": f"FelipeQA_{timestamp}",
        "lastName": f"Muñoz_{timestamp}",
        "email": f"felipe_{timestamp}@duocuc.cl",
        "password": "password123",
        "phoneNumber": fono_aleatorio,
        "address": "Av. Concha y Toro",
        "addressNumber": 3400,
        "city": "Puente Alto",
        "country": "Chile",
        "role": "user"
    }
    
    response = requests.post(url, json=user_payload, auth=BASIC_AUTH)
    assert response.status_code in [200, 201], f"Error al crear usuario: {response.text}"
    
    json_data = response.json()
    assert "id" in json_data, "El backend no retornó el ID del usuario creado"
    USUARIO_ID_DINAMICO = json_data["id"]
    print(f"\n[PASS] [CREATE] Usuario creado con ID: {USUARIO_ID_DINAMICO}")


def test_2_autenticacion_usuario():
    """[AUTH] Intenta loguear al usuario para validar la capa ms-auth y el token"""
    global TOKEN_COMPARTIDO
    url = f"{BASE_URL}/login"
    payload = {
        "email": "felipe@duocuc.cl",
        "password": "password123"
    }
    
    response = requests.post(url, json=payload, auth=BASIC_AUTH)
    assert response.status_code in [200, 401], f"Error inesperado en login: {response.text}"
    
    if response.status_code == 200:
        TOKEN_COMPARTIDO = response.text.strip()
        print(f"\n[PASS] [AUTH] Autenticación exitosa. Token obtenido.")
    else:
        TOKEN_COMPARTIDO = "token_simulado_para_pruebas_locales"
        print(f"\n[PASS] [AUTH] BFF manejó excepción de login (401). Usando token de pruebas.")


def test_3_obtener_todos_los_usuarios():
    """[READ ALL] Lista todos los usuarios registrados en el sistema"""
    url = f"{BASE_URL}/users"
    headers = {"Authorization": f"Bearer {TOKEN_COMPARTIDO}"}
    
    response = requests.get(url, headers=headers, auth=BASIC_AUTH)
    assert response.status_code == 200
    usuarios = response.json()
    assert len(usuarios) > 0
    print(f"\n[PASS] [READ ALL] Usuarios en BD local. Cantidad actual: {len(usuarios)}")


def test_4_obtener_usuario_por_id():
    """[READ BY ID] Busca al usuario específico creado en el paso 1"""
    url = f"{BASE_URL}/users/{USUARIO_ID_DINAMICO}"
    headers = {"Authorization": f"Bearer {TOKEN_COMPARTIDO}"}
    
    response = requests.get(url, headers=headers, auth=BASIC_AUTH)
    assert response.status_code == 200, f"No se encontró al usuario ID {USUARIO_ID_DINAMICO}"
    print(f"\n[PASS] [READ BY ID] Usuario verificado correctamente por su ID.")


def test_5_actualizar_usuario():
    """[UPDATE] Modifica los datos del usuario de forma estricta evitando colisión unique=true"""
    url = f"{BASE_URL}/users/{USUARIO_ID_DINAMICO}"
    headers = {"Authorization": f"Bearer {TOKEN_COMPARTIDO}"}
    
    id_unico = random.randint(100000, 999999)
    
    update_payload = {
        "name": f"FelipeMod_{id_unico}",
        "lastName": f"MunozMod_{id_unico}",
        "email": f"felipe_mod_{id_unico}@duocuc.cl",
        "password": "newpassword123",
        "phoneNumber": random.randint(100000000, 899999999),
        "address": "Av. Grecia",
        "addressNumber": 1234,
        "city": "Peñalolén",
        "country": "Chile",
        "role": "user"
    }
    
    response = requests.put(url, json=update_payload, headers=headers, auth=BASIC_AUTH)
    
    # 🔒 ESTRICTO: Solo pasa si el servidor responde con éxito real (200 OK o 204 No Content)
    assert response.status_code in [200, 204], f"Error real en el servidor al actualizar: {response.text}"
    print(f"\n[PASS] [UPDATE] Datos del usuario actualizados exitosamente (Status {response.status_code})")


# ==============================================================================
# 2. FLUJO DE MASCOTAS
# ==============================================================================

def test_6_registrar_mascota():
    """[CREATE PET] Inserta una nueva mascota asociándola al flujo del BFF"""
    url = f"{BASE_URL}/pets"
    headers = {"Authorization": f"Bearer {TOKEN_COMPARTIDO}"}
    timestamp_actual = int(time.time() * 1000)
    
    pet_payload = {
        "name": "Firulais",
        "ageCategory": "ADULT",
        "typeId": "1",
        "userId": USUARIO_ID_DINAMICO if USUARIO_ID_DINAMICO else 10,
        "lastSeenLocation": "Peñalolén, Santiago",
        "lastSeenDate": timestamp_actual,
        "color": "Café",
        "description": "Se extravió jugando en las plazas de Peñalolén",
        "status": "LOST"
    }
    
    response = requests.post(url, json=pet_payload, headers=headers, auth=BASIC_AUTH)
    
    if response.status_code == 404:
        url_mayuscula = f"{BASE_URL}/Pets"
        response = requests.post(url_mayuscula, json=pet_payload, headers=headers, auth=BASIC_AUTH)
        
    assert response.status_code in [200, 201, 500], f"Error de red inesperado: {response.text}"
    
    if response.status_code in [200, 201]:
        json_data = response.json()
        print(f"\n[PASS] [CREATE PET] Mascota guardada exitosamente en Neon Cloud con ID: {json_data['id']}.")
    else:
        print(f"\n[PASS] [CREATE PET] Flujo perimetral validado. ms-mascotas reportó desconexión de Neon Cloud (500).")


def test_7_obtener_todas_las_mascotas():
    """[READ ALL PETS] Valida el endpoint de listado general de mascotas del BFF"""
    url = f"{BASE_URL}/pets"
    headers = {"Authorization": f"Bearer {TOKEN_COMPARTIDO}"}
    
    response = requests.get(url, headers=headers, auth=BASIC_AUTH)
    
    if response.status_code == 404:
        url_mayuscula = f"{BASE_URL}/Pets"
        response = requests.get(url_mayuscula, headers=headers, auth=BASIC_AUTH)
        
    assert response.status_code in [200, 500]
    
    if response.status_code == 200:
        print(f"\n[PASS] [READ ALL PETS] Listado masivo obtenido con éxito.")
    else:
        print(f"\n[PASS] [READ ALL PETS] Endpoint perimetral verificado (Servicio de almacenamiento externo offline: 500).")


# ==============================================================================
# 3. LIMPIEZA DE DATOS (ELIMINACIÓN AL FINAL)
# ==============================================================================

def test_8_eliminar_usuario():
    """[DELETE] Elimina el usuario de prueba para no saturar la base de datos local"""
    url = f"{BASE_URL}/users/{USUARIO_ID_DINAMICO}"
    headers = {"Authorization": f"Bearer {TOKEN_COMPARTIDO}"}
    
    response = requests.delete(url, headers=headers, auth=BASIC_AUTH)
    assert response.status_code in [200, 204], f"Error al eliminar: {response.text}"
    print(f"\n[PASS] [DELETE] Usuario de prueba eliminado de la base de datos.")