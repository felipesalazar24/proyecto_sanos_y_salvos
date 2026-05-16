import { Header } from "@/components/header"
import { Footer } from "@/components/footer"
import { ReportForm } from "@/components/report-form"

export default function ReportarPage() {
  return (
    <div className="min-h-screen flex flex-col">
      <Header />
      
      <main className="flex-1 container mx-auto px-4 py-8">
        <div className="max-w-2xl mx-auto">
          <div className="mb-8 text-center">
            <h1 className="text-3xl font-bold mb-2">Crear Nuevo Reporte</h1>
            <p className="text-muted-foreground">
              Completa el formulario para reportar una mascota perdida o encontrada.
              Nuestro sistema de coincidencias comenzará a trabajar automáticamente.
            </p>
          </div>

          <ReportForm />
        </div>
      </main>

      <Footer />
    </div>
  )
}
