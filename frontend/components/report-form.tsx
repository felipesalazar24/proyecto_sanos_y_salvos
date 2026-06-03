"use client"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card"
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs"
import { Upload, MapPin, Send, Dog, Cat, Bird, HelpCircle } from "lucide-react"
import { PetType, PetSize, ReportType } from "@/lib/types"

interface ReportFormProps {
  defaultType?: ReportType
  onSubmit?: (data: FormData) => void
}

export function ReportForm({ defaultType = 'perdido', onSubmit }: ReportFormProps) {
  const [reportType, setReportType] = useState<ReportType>(defaultType)
  const [petType, setPetType] = useState<PetType>('perro')
  const [petSize, setPetSize] = useState<PetSize>('mediano')
  const [imagePreview, setImagePreview] = useState<string | null>(null)
  const [isSubmitting, setIsSubmitting] = useState(false)

  const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0]
    if (file) {
      const reader = new FileReader()
      reader.onloadend = () => {
        setImagePreview(reader.result as string)
      }
      reader.readAsDataURL(file)
    }
  }

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setIsSubmitting(true)
    
    const formData = new FormData(e.currentTarget)
    formData.set('type', reportType)
    formData.set('petType', petType)
    formData.set('size', petSize)
    
    // Simulate API call
    await new Promise(resolve => setTimeout(resolve, 1500))
    
    onSubmit?.(formData)
    setIsSubmitting(false)
    
    // Show success message (in a real app, this would redirect or show a toast)
    alert('Reporte enviado exitosamente. Te notificaremos si encontramos coincidencias.')
  }

  const petTypeOptions = [
    { value: 'perro', label: 'Perro', icon: Dog },
    { value: 'gato', label: 'Gato', icon: Cat },
    { value: 'ave', label: 'Ave', icon: Bird },
    { value: 'otro', label: 'Otro', icon: HelpCircle },
  ]

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <Tabs value={reportType} onValueChange={(v) => setReportType(v as ReportType)}>
        <TabsList className="grid w-full grid-cols-2">
          <TabsTrigger value="perdido" className="gap-2">
            <span className="h-2 w-2 rounded-full bg-destructive" />
            Perdí mi mascota
          </TabsTrigger>
          <TabsTrigger value="encontrado" className="gap-2">
            <span className="h-2 w-2 rounded-full bg-accent" />
            Encontré una mascota
          </TabsTrigger>
        </TabsList>
        
        <TabsContent value="perdido" className="mt-4">
          <p className="text-sm text-muted-foreground mb-4">
            Completa los datos de tu mascota perdida. Mientras más información proporciones, 
            mejor será la probabilidad de encontrar coincidencias.
          </p>
        </TabsContent>
        
        <TabsContent value="encontrado" className="mt-4">
          <p className="text-sm text-muted-foreground mb-4">
            Has encontrado una mascota y quieres ayudar a reunirla con su dueño. 
            Ingresa los datos del animal que encontraste.
          </p>
        </TabsContent>
      </Tabs>

      {/* Image Upload */}
      <Card>
        <CardHeader className="pb-3">
          <CardTitle className="text-base">Foto de la Mascota</CardTitle>
          <CardDescription>
            Una foto clara ayuda significativamente en la identificación.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="flex flex-col items-center gap-4">
            {imagePreview ? (
              <div className="relative w-full max-w-xs aspect-square rounded-lg overflow-hidden">
                <img
                  src={imagePreview}
                  alt="Preview"
                  className="w-full h-full object-cover"
                />
                <Button
                  type="button"
                  variant="secondary"
                  size="sm"
                  className="absolute bottom-2 right-2"
                  onClick={() => setImagePreview(null)}
                >
                  Cambiar foto
                </Button>
              </div>
            ) : (
              <label className="flex flex-col items-center justify-center w-full max-w-xs aspect-square border-2 border-dashed border-muted-foreground/25 rounded-lg cursor-pointer hover:border-primary/50 transition-colors">
                <Upload className="h-10 w-10 text-muted-foreground mb-2" />
                <span className="text-sm text-muted-foreground">Haz clic para subir</span>
                <span className="text-xs text-muted-foreground mt-1">PNG, JPG hasta 10MB</span>
                <input
                  type="file"
                  name="image"
                  accept="image/*"
                  className="hidden"
                  onChange={handleImageChange}
                />
              </label>
            )}
          </div>
        </CardContent>
      </Card>

      {/* Pet Information */}
      <Card>
        <CardHeader className="pb-3">
          <CardTitle className="text-base">Información de la Mascota</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          {reportType === 'perdido' && (
            <div className="space-y-2">
              <Label htmlFor="petName">Nombre de la mascota</Label>
              <Input
                id="petName"
                name="petName"
                placeholder="Ej: Max, Luna, Michi..."
              />
            </div>
          )}

          <div className="space-y-2">
            <Label>Tipo de mascota</Label>
            <div className="grid grid-cols-4 gap-2">
              {petTypeOptions.map((option) => (
                <button
                  key={option.value}
                  type="button"
                  onClick={() => setPetType(option.value as PetType)}
                  className={`flex flex-col items-center gap-1 p-3 rounded-lg border-2 transition-colors ${
                    petType === option.value
                      ? 'border-primary bg-primary/5'
                      : 'border-muted hover:border-primary/50'
                  }`}
                >
                  <option.icon className={`h-6 w-6 ${petType === option.value ? 'text-primary' : 'text-muted-foreground'}`} />
                  <span className={`text-xs ${petType === option.value ? 'font-medium' : ''}`}>{option.label}</span>
                </button>
              ))}
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="breed">Raza</Label>
              <Input
                id="breed"
                name="breed"
                placeholder="Ej: Golden Retriever"
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="color">Color</Label>
              <Input
                id="color"
                name="color"
                placeholder="Ej: Dorado, Negro..."
                required
              />
            </div>
          </div>

          <div className="space-y-2">
            <Label>Tamaño</Label>
            <Select value={petSize} onValueChange={(v) => setPetSize(v as PetSize)}>
              <SelectTrigger>
                <SelectValue />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="pequeño">Pequeño (menos de 10kg)</SelectItem>
                <SelectItem value="mediano">Mediano (10-25kg)</SelectItem>
                <SelectItem value="grande">Grande (más de 25kg)</SelectItem>
              </SelectContent>
            </Select>
          </div>

          <div className="space-y-2">
            <Label htmlFor="description">Descripción y señas particulares</Label>
            <Textarea
              id="description"
              name="description"
              placeholder="Describe características distintivas: collar, cicatrices, comportamiento, etc."
              rows={3}
              required
            />
          </div>
        </CardContent>
      </Card>

      {/* Location */}
      <Card>
        <CardHeader className="pb-3">
          <CardTitle className="text-base flex items-center gap-2">
            <MapPin className="h-4 w-4" />
            Ubicación
          </CardTitle>
          <CardDescription>
            {reportType === 'perdido' 
              ? '¿Dónde viste a tu mascota por última vez?' 
              : '¿Dónde encontraste a la mascota?'
            }
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="address">Dirección o referencia</Label>
            <Input
              id="address"
              name="address"
              placeholder="Ej: Parque O'Higgins, cerca de la entrada principal"
              required
            />
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="city">Comuna/Ciudad</Label>
              <Input
                id="city"
                name="city"
                placeholder="Ej: Santiago"
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="date">Fecha</Label>
              <Input
                id="date"
                name="date"
                type="date"
                defaultValue={new Date().toISOString().split('T')[0]}
                required
              />
            </div>
          </div>
        </CardContent>
      </Card>

      {/* Contact Information */}
      <Card>
        <CardHeader className="pb-3">
          <CardTitle className="text-base">Información de Contacto</CardTitle>
          <CardDescription>
            Tus datos estarán protegidos y solo se compartirán cuando haya una coincidencia verificada.
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="contactName">Nombre completo</Label>
            <Input
              id="contactName"
              name="contactName"
              placeholder="Tu nombre"
              required
            />
          </div>
          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="contactPhone">Teléfono</Label>
              <Input
                id="contactPhone"
                name="contactPhone"
                type="tel"
                placeholder="+56 9 1234 5678"
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="contactEmail">Email (opcional)</Label>
              <Input
                id="contactEmail"
                name="contactEmail"
                type="email"
                placeholder="tu@email.com"
              />
            </div>
          </div>
        </CardContent>
      </Card>

      <Button type="submit" size="lg" className="w-full gap-2" disabled={isSubmitting}>
        {isSubmitting ? (
          <>Enviando reporte...</>
        ) : (
          <>
            <Send className="h-4 w-4" />
            Publicar Reporte
          </>
        )}
      </Button>
    </form>
  )
}
