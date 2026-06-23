'use client';

import { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { MapPin, Send, Info } from 'lucide-react';

const ANIMAL_TYPES: Record<string, string[]> = {
  perro: ['Husky', 'Doberman', 'Pastor Alemán', 'Labrador', 'Cocker Spaniel', 'Bulldog', 'Caniche', 'Beagle', 'Chihuahua', 'Otro'],
  gato: ['Naranjo', 'Calico', 'Egipcio', 'Siamés', 'Persa', 'Bengalí', 'Ragdoll', 'Otro'],
  ave: ['Loro', 'Canario', 'Paloma', 'Gallina', 'Ganso', 'Otro'],
  otro: ['Conejo', 'Hamster', 'Tortuga', 'Otro']
};

const COLORS = [
  'Negro',
  'Blanco',
  'Gris',
  'Marrón',
  'Dorado',
  'Rojo',
  'Blanco con Negro',
  'Blanco con Marrón',
  'Blanco con Gris',
  'Otro'
];

const AGE_CATEGORIES = ['Joven', 'Adulto', 'Viejo'];

interface ReportFormProps {
  userCountry?: string;
  userCity?: string;
}

export function ReportForm({ 
  userCountry = 'Chile',
  userCity = ''
}: ReportFormProps) {
  const [status, setStatus] = useState<'extraviado' | 'encontrado'>('extraviado');
  const [animalType, setAnimalType] = useState<string>('perro');
  const [breed, setBreed] = useState<string>('');
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false);
  const [error, setError] = useState<string>('');
  const [success, setSuccess] = useState<string>('');
  const [petName, setPetName] = useState<string>('');

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setIsSubmitting(true);

    try {
      const formData = new FormData(e.currentTarget);
      const data = {
        name: formData.get('petName') || 'Desconocido',
        ageCategory: formData.get('ageCategory'),
        type: animalType,
        breed: breed,
        color: formData.get('color'),
        description: formData.get('description'),
        lastSeenLocation: formData.get('lastSeenLocation'),
        lastSeenDate: formData.get('lastSeenDate'),
        status: status,
        contactName: formData.get('contactName'),
        contactPhone: formData.get('contactPhone'),
        contactEmail: formData.get('contactEmail') || null,
      };

      console.log('Report data:', data);
      setSuccess('Reporte enviado exitosamente. Te notificaremos si encontramos coincidencias.');
      
      e.currentTarget.reset();
      setPetName('');
      setBreed('');
      setAnimalType('perro');
    } catch (err: any) {
      setError(err.message || 'Error al enviar el reporte');
    } finally {
      setIsSubmitting(false);
    }
  };

  const availableBreeds = ANIMAL_TYPES[animalType] || [];
  const isFoundReport = status === 'encontrado';

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <Tabs value={status} onValueChange={(v) => setStatus(v as 'extraviado' | 'encontrado')}>
        <TabsList className="grid w-full grid-cols-2">
          <TabsTrigger value="extraviado" className="gap-2">
            <span className="h-2 w-2 rounded-full bg-destructive" />
            Perdí mi mascota
          </TabsTrigger>
          <TabsTrigger value="encontrado" className="gap-2">
            <span className="h-2 w-2 rounded-full bg-accent" />
            Encontré una mascota
          </TabsTrigger>
        </TabsList>

        <TabsContent value="extraviado" className="mt-4">
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

      <Card>
        <CardHeader className="pb-3">
          <CardTitle className="text-base">Información de la Mascota</CardTitle>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <div className="flex items-center justify-between">
              <Label htmlFor="petName">Nombre de la mascota</Label>
              {isFoundReport && (
                <span className="text-xs text-muted-foreground bg-blue-50 px-2 py-1 rounded">
                  Opcional - Puedes poner "Desconocido"
                </span>
              )}
            </div>
            <Input
              id="petName"
              name="petName"
              value={petName}
              onChange={(e) => setPetName(e.target.value)}
              placeholder={isFoundReport ? "Ej: Desconocido, Max, etc..." : "Ej: Max, Luna, Michi..."}
              required={!isFoundReport}
            />
            {isFoundReport && (
              <p className="text-xs text-muted-foreground flex items-center gap-1 mt-1">
                <Info className="h-3 w-3" />
                Si no sabes el nombre, escribe "Desconocido"
              </p>
            )}
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="animalType">Tipo de Animal</Label>
              <Select value={animalType} onValueChange={setAnimalType}>
                <SelectTrigger id="animalType">
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="perro">Perro</SelectItem>
                  <SelectItem value="gato">Gato</SelectItem>
                  <SelectItem value="ave">Ave</SelectItem>
                  <SelectItem value="otro">Otro</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div className="space-y-2">
              <Label htmlFor="breed">Raza</Label>
              <Select value={breed} onValueChange={setBreed}>
                <SelectTrigger id="breed">
                  <SelectValue placeholder="Selecciona raza" />
                </SelectTrigger>
                <SelectContent>
                  {availableBreeds.map((b) => (
                    <SelectItem key={b} value={b}>
                      {b}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="ageCategory">Categoría de Edad</Label>
              <Select name="ageCategory" defaultValue="adulto">
                <SelectTrigger id="ageCategory">
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  {AGE_CATEGORIES.map((age) => (
                    <SelectItem key={age} value={age.toLowerCase()}>
                      {age}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="space-y-2">
              <Label htmlFor="color">Color</Label>
              <Select name="color" required>
                <SelectTrigger id="color">
                  <SelectValue placeholder="Selecciona color" />
                </SelectTrigger>
                <SelectContent>
                  {COLORS.map((color) => (
                    <SelectItem key={color} value={color}>
                      {color}
                    </SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>
          </div>

          <div className="space-y-2">
            <Label htmlFor="description">
              {isFoundReport ? 'Estado y Características' : 'Descripción y Características'}
            </Label>
            <Textarea
              id="description"
              name="description"
              placeholder={
                isFoundReport
                  ? "Describe el estado de la mascota al encontrarla:\n- ¿Tiene collar o identificación?\n- ¿Tiene heridas o signos de maltrato?\n- ¿Parece asustada o agresiva?\n- ¿Está desnutrida o enferma?\n- Otras características distintivas..."
                  : "Describe características distintivas: collar, cicatrices, comportamiento, alergias, etc."
              }
              rows={4}
              required
            />
            {isFoundReport && (
              <p className="text-xs text-muted-foreground flex items-center gap-1 mt-1">
                <Info className="h-3 w-3" />
                Incluye detalles sobre heridas, salud, comportamiento y cualquier identificación que veas
              </p>
            )}
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader className="pb-3">
          <CardTitle className="text-base flex items-center gap-2">
            <MapPin className="h-4 w-4" />
            Ubicación
          </CardTitle>
          <CardDescription>
            {status === 'extraviado'
              ? '¿Dónde viste a tu mascota por última vez?'
              : '¿Dónde encontraste a la mascota?'
            }
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="space-y-2">
            <Label htmlFor="lastSeenLocation">Comuna/Localidad</Label>
            <Input
              id="lastSeenLocation"
              name="lastSeenLocation"
              placeholder="Ej: La Florida, Ñuñoa, Las Condes..."
              required
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label>País</Label>
              <Input
                value={userCountry || 'Chile'}
                disabled
                className="bg-muted cursor-not-allowed"
              />
            </div>
            <div className="space-y-2">
              <Label>Ciudad</Label>
              <Input
                value={userCity || 'Tu ciudad'}
                disabled
                className="bg-muted cursor-not-allowed"
              />
            </div>
          </div>

          <div className="space-y-2">
            <Label htmlFor="lastSeenDate">
              {isFoundReport ? 'Fecha de encuentro' : 'Fecha que fue visto'}
            </Label>
            <Input
              id="lastSeenDate"
              name="lastSeenDate"
              type="date"
              defaultValue={new Date().toISOString().split('T')[0]}
              required
            />
          </div>
        </CardContent>
      </Card>

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
              <Label htmlFor="contactEmail">Email (Opcional)</Label>
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

      {error && (
        <div className="p-3 bg-destructive/10 border border-destructive text-destructive rounded-lg text-sm">
          {error}
        </div>
      )}

      {success && (
        <div className="p-3 bg-green-500/10 border border-green-500 text-green-700 rounded-lg text-sm">
          {success}
        </div>
      )}

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
  );
}