'use client';

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { Header } from "@/components/header";
import { Footer } from "@/components/footer";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  User,
  PawPrint,
  Bell,
  Settings,
  LogOut,
  MapPin,
  Calendar,
  Edit,
  Eye
} from "lucide-react";
import { mockReports } from "@/lib/mock-data";
import Link from "next/link";

export default function PerfilPage() {
  const router = useRouter();

  // 👇 Usuario cargado desde localStorage (guardado en login)
  const [user, setUser] = useState<{ name: string, email: string, phone?: string, memberSince?: string } | null>(null);

  useEffect(() => {
    // Simulación: Lee usuario desde localStorage (puedes mejorar para usar contexto/global store/JWT decode)
    const token = localStorage.getItem("token");
    const email = localStorage.getItem("user_email"); // Guarda este dato en el login
    const name = localStorage.getItem("user_name");   // Igual, debería ser llenado en login
    // Extra: podrías guardar un objeto stringificado con más datos en el login

    // Si no hay token, redirección a login
    if (!token) {
      router.push("/login");
      return;
    }
    setUser({
      name: name || "Usuario",
      email: email || "",
      phone: "+56 9 1234 5678",     // Simula/ajusta según tus datos
      memberSince: "Enero 2026"     // Simula/ajusta según tus datos
    });
  }, [router]);

  // 👇 Handler para cerrar sesión
  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user_email');
    localStorage.removeItem('user_name');
    // Quita otros datos si los guardaste
    router.push('/login');
  };

  // Puedes dejar esta simulación, pero lo ideal es traer reportes del usuario autenticado
  const userReports = mockReports.slice(0, 2);
  const activeReports = userReports.filter(r => r.status === 'activo');

  // Mientras carga el user
  if (!user) {
    return <div className="min-h-screen flex items-center justify-center">Cargando...</div>;
  }

  return (
    <div className="min-h-screen flex flex-col">
      <Header />

      <main className="flex-1 container mx-auto px-4 py-8">
        <div className="max-w-4xl mx-auto space-y-6">
          {/* Profile Header */}
          <Card>
            <CardContent className="py-6">
              <div className="flex flex-col sm:flex-row items-center gap-6">
                <div className="h-20 w-20 rounded-full bg-primary/10 flex items-center justify-center">
                  <User className="h-10 w-10 text-primary" />
                </div>
                <div className="text-center sm:text-left flex-1">
                  <h1 className="text-2xl font-bold">{user.name}</h1>
                  <p className="text-muted-foreground">{user.email}</p>
                  <p className="text-sm text-muted-foreground">Miembro desde {user.memberSince}</p>
                </div>
                <div className="flex gap-2">
                  <Button variant="outline" size="sm" className="gap-2">
                    <Edit className="h-4 w-4" />
                    Editar Perfil
                  </Button>
                  <Button variant="outline" size="sm" className="gap-2">
                    <Settings className="h-4 w-4" />
                    Configuración
                  </Button>
                </div>
              </div>
            </CardContent>
          </Card>

          {/* Stats */}
          <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
            <Card>
              <CardContent className="pt-6 text-center">
                <div className="text-3xl font-bold text-primary">{userReports.length}</div>
                <div className="text-sm text-muted-foreground">Mis Reportes</div>
              </CardContent>
            </Card>
            <Card>
              <CardContent className="pt-6 text-center">
                <div className="text-3xl font-bold text-accent-foreground">{activeReports.length}</div>
                <div className="text-sm text-muted-foreground">Activos</div>
              </CardContent>
            </Card>
            <Card>
              <CardContent className="pt-6 text-center">
                <div className="text-3xl font-bold">1</div>
                <div className="text-sm text-muted-foreground">Reencuentros</div>
              </CardContent>
            </Card>
            <Card>
              <CardContent className="pt-6 text-center">
                <div className="text-3xl font-bold">3</div>
                <div className="text-sm text-muted-foreground">Alertas</div>
              </CardContent>
            </Card>
          </div>

          {/* Tabs */}
          <Tabs defaultValue="reports">
            <TabsList className="grid w-full grid-cols-3">
              <TabsTrigger value="reports" className="gap-2">
                <PawPrint className="h-4 w-4" />
                Mis Reportes
              </TabsTrigger>
              <TabsTrigger value="alerts" className="gap-2">
                <Bell className="h-4 w-4" />
                Alertas
              </TabsTrigger>
              <TabsTrigger value="settings" className="gap-2">
                <Settings className="h-4 w-4" />
                Ajustes
              </TabsTrigger>
            </TabsList>

            <TabsContent value="reports" className="mt-6 space-y-4">
              {userReports.length === 0 ? (
                <Card>
                  <CardContent className="py-12 text-center">
                    <PawPrint className="h-12 w-12 text-muted-foreground mx-auto mb-4" />
                    <h3 className="font-semibold mb-2">No tienes reportes</h3>
                    <p className="text-muted-foreground mb-4">
                      Crea tu primer reporte para comenzar a buscar o ayudar a reunir mascotas.
                    </p>
                    <Link href="/reportar">
                      <Button>Crear Reporte</Button>
                    </Link>
                  </CardContent>
                </Card>
              ) : (
                userReports.map((report) => (
                  <Card key={report.id}>
                    <CardContent className="py-4">
                      <div className="flex gap-4">
                        {report.imageUrl && (
                          <img
                            src={report.imageUrl}
                            alt={report.petName || 'Mascota'}
                            className="h-20 w-20 rounded-lg object-cover"
                            crossOrigin="anonymous"
                          />
                        )}
                        <div className="flex-1">
                          <div className="flex items-start justify-between">
                            <div>
                              <div className="flex items-center gap-2 mb-1">
                                <h3 className="font-semibold">
                                  {report.petName || report.petType}
                                </h3>
                                <Badge variant={report.type === 'perdido' ? 'destructive' : 'default'}>
                                  {report.type === 'perdido' ? 'Perdido' : 'Encontrado'}
                                </Badge>
                                <Badge variant={report.status === 'activo' ? 'outline' : 'secondary'}>
                                  {report.status}
                                </Badge>
                              </div>
                              <div className="flex items-center gap-4 text-sm text-muted-foreground">
                                <span className="flex items-center gap-1">
                                  <MapPin className="h-3 w-3" />
                                  {report.location.city}
                                </span>
                                <span className="flex items-center gap-1">
                                  <Calendar className="h-3 w-3" />
                                  {new Date(report.createdAt).toLocaleDateString('es-CL')}
                                </span>
                              </div>
                            </div>
                            <div className="flex gap-2">
                              <Link href={`/reporte/${report.id}`}>
                                <Button variant="outline" size="sm" className="gap-1">
                                  <Eye className="h-3 w-3" />
                                  Ver
                                </Button>
                              </Link>
                              <Button variant="outline" size="sm" className="gap-1">
                                <Edit className="h-3 w-3" />
                                Editar
                              </Button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </CardContent>
                  </Card>
                ))
              )}

              <Link href="/reportar">
                <Button variant="outline" className="w-full gap-2">
                  <PawPrint className="h-4 w-4" />
                  Crear Nuevo Reporte
                </Button>
              </Link>
            </TabsContent>

            <TabsContent value="alerts" className="mt-6">
              <Card>
                <CardHeader>
                  <CardTitle>Configuración de Alertas</CardTitle>
                  <CardDescription>
                    Administra cómo y cuándo recibir notificaciones de posibles coincidencias.
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-4">
                  <div className="flex items-center justify-between py-2 border-b">
                    <div>
                      <p className="font-medium">Alertas por Email</p>
                      <p className="text-sm text-muted-foreground">Recibe notificaciones en tu correo</p>
                    </div>
                    <Badge variant="secondary">Activo</Badge>
                  </div>
                  <div className="flex items-center justify-between py-2 border-b">
                    <div>
                      <p className="font-medium">Alertas en la App</p>
                      <p className="text-sm text-muted-foreground">Notificaciones push en la plataforma</p>
                    </div>
                    <Badge variant="secondary">Activo</Badge>
                  </div>
                  <div className="flex items-center justify-between py-2">
                    <div>
                      <p className="font-medium">Resumen Semanal</p>
                      <p className="text-sm text-muted-foreground">Recibe un resumen de actividad cada semana</p>
                    </div>
                    <Badge variant="outline">Inactivo</Badge>
                  </div>
                </CardContent>
              </Card>
            </TabsContent>

            <TabsContent value="settings" className="mt-6 space-y-4">
              <Card>
                <CardHeader>
                  <CardTitle>Información Personal</CardTitle>
                  <CardDescription>
                    Actualiza tu información de contacto.
                  </CardDescription>
                </CardHeader>
                <CardContent className="space-y-4">
                  <div className="grid gap-4 sm:grid-cols-2">
                    <div>
                      <p className="text-sm text-muted-foreground">Nombre</p>
                      <p className="font-medium">{user.name}</p>
                    </div>
                    <div>
                      <p className="text-sm text-muted-foreground">Email</p>
                      <p className="font-medium">{user.email}</p>
                    </div>
                    <div>
                      <p className="text-sm text-muted-foreground">Teléfono</p>
                      <p className="font-medium">{user.phone}</p>
                    </div>
                    <div>
                      <p className="text-sm text-muted-foreground">Miembro desde</p>
                      <p className="font-medium">{user.memberSince}</p>
                    </div>
                  </div>
                  <Button variant="outline">Editar Información</Button>
                </CardContent>
              </Card>

              <Card className="border-destructive/50">
                <CardHeader>
                  <CardTitle className="text-destructive">Zona de Peligro</CardTitle>
                </CardHeader>
                <CardContent className="space-y-4">
                  <Button
                    variant="outline"
                    className="gap-2 w-full sm:w-auto"
                    onClick={handleLogout} // 👈 Aquí actúa el logout real
                  >
                    <LogOut className="h-4 w-4" />
                    Cerrar Sesión
                  </Button>
                  <Button variant="destructive" className="ml-2">
                    Eliminar Cuenta
                  </Button>
                </CardContent>
              </Card>
            </TabsContent>
          </Tabs>
        </div>
      </main>

      <Footer />
    </div>
  )
}