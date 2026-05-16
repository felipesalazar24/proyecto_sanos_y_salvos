import { Header } from "@/components/header"
import { Footer } from "@/components/footer"
import { AlertsList } from "@/components/match-notification"
import { mockReports, mockMatches } from "@/lib/mock-data"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Bell, Settings } from "lucide-react"
import { Button } from "@/components/ui/button"
import Link from "next/link"

export default function AlertasPage() {
  const pendingCount = mockMatches.filter(m => m.status === 'pendiente').length

  return (
    <div className="min-h-screen flex flex-col">
      <Header />
      
      <main className="flex-1 container mx-auto px-4 py-8">
        <div className="max-w-3xl mx-auto space-y-6">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-3xl font-bold mb-2">Alertas y Coincidencias</h1>
              <p className="text-muted-foreground">
                El motor de coincidencias analiza automáticamente los reportes para detectar posibles matches.
              </p>
            </div>
            <Button variant="outline" size="icon">
              <Settings className="h-4 w-4" />
            </Button>
          </div>

          {/* Stats */}
          <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
            <Card>
              <CardContent className="pt-6">
                <div className="flex items-center gap-4">
                  <div className="h-12 w-12 rounded-full bg-primary/10 flex items-center justify-center">
                    <Bell className="h-6 w-6 text-primary" />
                  </div>
                  <div>
                    <div className="text-2xl font-bold">{pendingCount}</div>
                    <div className="text-sm text-muted-foreground">Alertas Pendientes</div>
                  </div>
                </div>
              </CardContent>
            </Card>

            <Card>
              <CardContent className="pt-6">
                <div className="flex items-center gap-4">
                  <div className="h-12 w-12 rounded-full bg-accent/20 flex items-center justify-center">
                    <Bell className="h-6 w-6 text-accent-foreground" />
                  </div>
                  <div>
                    <div className="text-2xl font-bold">12</div>
                    <div className="text-sm text-muted-foreground">Reencuentros Exitosos</div>
                  </div>
                </div>
              </CardContent>
            </Card>

            <Card>
              <CardContent className="pt-6">
                <div className="flex items-center gap-4">
                  <div className="h-12 w-12 rounded-full bg-muted flex items-center justify-center">
                    <Bell className="h-6 w-6 text-muted-foreground" />
                  </div>
                  <div>
                    <div className="text-2xl font-bold">3</div>
                    <div className="text-sm text-muted-foreground">Mis Reportes Activos</div>
                  </div>
                </div>
              </CardContent>
            </Card>
          </div>

          {/* Notification Settings Info */}
          <Card className="border-muted bg-muted/50">
            <CardContent className="py-4">
              <div className="flex items-center gap-3">
                <Bell className="h-5 w-5 text-muted-foreground" />
                <p className="text-sm text-muted-foreground">
                  Las notificaciones están activas. Recibirás alertas por email cuando se detecte una coincidencia.
                </p>
              </div>
            </CardContent>
          </Card>

          {/* Alerts List */}
          <div className="space-y-4">
            <div className="flex items-center justify-between">
              <h2 className="text-xl font-semibold">Posibles Coincidencias</h2>
              {pendingCount > 0 && (
                <Badge variant="secondary">{pendingCount} pendiente{pendingCount > 1 ? 's' : ''}</Badge>
              )}
            </div>
            
            <AlertsList reports={mockReports} matches={mockMatches} />
          </div>

          {/* CTA */}
          <Card>
            <CardHeader>
              <CardTitle>¿No encuentras lo que buscas?</CardTitle>
              <CardDescription>
                Puedes buscar manualmente en el mapa o crear un nuevo reporte para activar el motor de coincidencias.
              </CardDescription>
            </CardHeader>
            <CardContent className="flex gap-4">
              <Link href="/mapa">
                <Button variant="outline">Ver Mapa</Button>
              </Link>
              <Link href="/reportar">
                <Button>Crear Reporte</Button>
              </Link>
            </CardContent>
          </Card>
        </div>
      </main>

      <Footer />
    </div>
  )
}
