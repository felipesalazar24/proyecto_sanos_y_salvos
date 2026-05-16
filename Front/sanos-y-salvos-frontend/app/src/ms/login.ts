// src/ms/login.ts

export interface LoginParams {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  // Agrega otros campos del backend si aplica
}

export async function loginUser(params: LoginParams): Promise<LoginResponse> {
  const response = await fetch('http://localhost:8084/api/v1/bff/web/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(params)
  });
  if (!response.ok) {
    throw new Error('Login fallido');
  }
  return response.json();
}