"use client"

import { useState } from "react"
import { Header } from "@/components/header"
import { Footer } from "@/components/footer"
import { MapWrapper } from "@/components/map-wrapper"
import { ReportCard } from "@/components/report-card"
import { ReportFilters } from "@/components/report-filters"
import { mockReports } from "@/lib/mock-data"
import { PetReport } from "@/lib/types"
import { Button } from "@/components/ui/button"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Map, List, PawPrint } from "lucide-react"
import Link from "next/link"

export default function MapaPage() {
  const [filteredReports, setFilteredReports] = useState<PetReport[]>(mockReports)
  const [selectedReport, setSelectedReport] = useState<string | null>(null)
  const [view, setView] = useState<"map" | "list">("map")

  const handleMarkerClick = (report: PetReport) => {
    setSelectedReport(report.id)
  }

  const lostCount = filteredReports.filter(r => r.type === 'perdido').length
  const foundCount = filteredReports.filter(r => r.type === 'encontrado').length

  return (
    <div className="min-h-screen flex flex-col">
      <Header />
      
      <main className="flex-1 container mx-auto px-4 py-6">
        <div className="flex flex-col lg:flex-row gap-6 h-full">
          {/* Sidebar */}
          <div className="lg:w-96 space-y-4">
            <div className="flex items-center justify-between">
              <div>
                <h1 className="text-2xl font-bold">Mapa de Reportes</h1>
                <p className="text-sm text-muted-foreground">
                  {filteredReports.length} reportes activos • {lostCount} perdidos • {foundCount} encontrados
                </p>
              </div>
              <Link href="/reportar">
                <Button size="sm" className="gap-2">
                  <PawPrint className="h-4 w-4" />
                  Reportar
                </Button>
              </Link>
            </div>

            <ReportFilters reports={mockReports} onFilter={setFilteredReports} />

            {/* View Toggle for Mobile */}
            <Tabs value={view} onValueChange={(v) => setView(v as "map" | "list")} className="w-full">
              <div className="lg:hidden">
                <TabsList className="grid w-full grid-cols-2">
                  <TabsTrigger value="map" className="gap-2">
                    <Map className="h-4 w-4" />
                    Mapa
                  </TabsTrigger>
                  <TabsTrigger value="list" className="gap-2">
                    <List className="h-4 w-4" />
                    Lista
                  </TabsTrigger>
                </TabsList>
              </div>

              {/* Report List - Desktop */}
              <div className="hidden lg:block mt-4 space-y-4 max-h-[calc(100vh-280px)] overflow-y-auto pr-2">
                {filteredReports.length === 0 ? (
                  <div className="text-center py-8 text-muted-foreground">
                    <p>No se encontraron reportes con los filtros seleccionados.</p>
                  </div>
                ) : (
                  filteredReports.map((report) => (
                    <div
                      key={report.id}
                      className={`cursor-pointer transition-all ${
                        selectedReport === report.id ? 'ring-2 ring-primary rounded-lg' : ''
                      }`}
                      onClick={() => setSelectedReport(report.id)}
                    >
                      <ReportCard report={report} compact />
                    </div>
                  ))
                )}
              </div>

              {/* Report List - Mobile */}
              <TabsContent value="list" className="lg:hidden mt-4 space-y-4">
                {filteredReports.length === 0 ? (
                  <div className="text-center py-8 text-muted-foreground">
                    <p>No se encontraron reportes con los filtros seleccionados.</p>
                  </div>
                ) : (
                  filteredReports.map((report) => (
                    <ReportCard key={report.id} report={report} />
                  ))
                )}
              </TabsContent>
            </Tabs>
          </div>

          {/* Map Area */}
          <div className={`flex-1 ${view === 'list' ? 'hidden lg:block' : ''}`}>
            <div className="h-[400px] lg:h-[calc(100vh-180px)] rounded-lg overflow-hidden border">
              <MapWrapper
                reports={filteredReports}
                selectedReport={selectedReport}
                onMarkerClick={handleMarkerClick}
              />
            </div>
            
            {/* Legend */}
            <div className="mt-4 flex items-center gap-6 text-sm text-muted-foreground">
              <div className="flex items-center gap-2">
                <div className="h-4 w-4 rounded-full bg-destructive border-2 border-white shadow" />
                <span>Mascota Perdida</span>
              </div>
              <div className="flex items-center gap-2">
                <div className="h-4 w-4 rounded-full bg-accent border-2 border-white shadow" />
                <span>Mascota Encontrada</span>
              </div>
            </div>
          </div>
        </div>
      </main>

      <Footer />
    </div>
  )
}
