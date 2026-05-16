'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function LoginPage() {
  const router = useRouter();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    try {
      const response = await fetch('http://localhost:8084/api/v1/bff/web/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
      });

      if (!response.ok) {
        throw new Error('Usuario o contraseña incorrectos');
      }

      const data = await response.json();
      localStorage.setItem('token', data.token);
      router.push('/');
    } catch (err: any) {
      setError(err.message || 'Error en el login');
    }
  };

  return (
    <div style={{
      maxWidth: 400, margin: '60px auto', padding: 32,
      border: '1.5px solid #bc8a5f', borderRadius: 10, background: '#fff', boxShadow: '0 2px 16px rgba(188,138,95,0.1)'
    }}>
      <h2 style={{ textAlign: 'center', color: '#bc8a5f', marginBottom: 24 }}>Iniciar sesión</h2>
      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: 16 }}>
          <label>Correo electrónico:</label>
          <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            required
            style={{
              border: '2px solid #bc8a5f',
              borderRadius: 4,
              padding: 8,
              background: '#faf6f1',
              width: '100%'
            }}
          />
        </div>
        <div style={{ marginBottom: 16 }}>
          <label>Contraseña:</label>
          <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required
            style={{
              border: '2px solid #bc8a5f',
              borderRadius: 4,
              padding: 8,
              background: '#faf6f1',
              width: '100%'
            }}
          />
        </div>
        {error && (
          <div style={{ color: '#c1440e', marginBottom: 16, textAlign: 'center' }}>
            {error}
          </div>
        )}
        <button type="submit" style={{
          width: '100%',
          padding: 10,
          background: '#bc8a5f',
          color: '#fff',
          border: 'none',
          borderRadius: 4,
          fontWeight: 600,
        }}>
          Iniciar sesión
        </button>
      </form>
      <p style={{ marginTop: 24, textAlign: 'center' }}>
        ¿No tienes cuenta?{' '}
        <a href="/register" style={{ color: '#bc8a5f', textDecoration: 'underline' }}>
          Crear cuenta
        </a>
      </p>
    </div>
  );
}