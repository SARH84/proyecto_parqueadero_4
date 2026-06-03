// Define la función globalmente para que Google la encuentre
window.initMap = function() {
    console.log("Iniciando mapa...");
    
    // 1. Configuración inicial del mapa centrada en Ibagué
    const mapaVisual = new google.maps.Map(document.getElementById("map"), {
        zoom: 13,
        center: { lat: 4.4389, lng: -75.2322 }
    });

    // 2. Consumo del endpoint que creamos específicamente para coordenadas
    fetch('/api/trayectos/con-coordenadas', {
        method: 'GET',
        headers: { 
            'Content-Type': 'application/json'
            // OJO: Si tu seguridad requiere la APIKey en el header, déjala aquí. 
            // Si el endpoint es público, puedes quitar esta línea.
            // 'APIKey': '804c192f-cebf-4c18-a75a-892609fdbd45' 
        }
    })
    .then(response => {
        if (!response.ok) throw new Error('Error en la respuesta del servidor');
        return response.json();
    })
    .then(data => {
        console.log("Trayectos con coordenadas recibidos:", data);
        
        // 3. Iteración segura para pintar marcadores
        data.forEach(trayecto => {
            if (trayecto.latitud != null && trayecto.longitud != null) {
                const marker = new google.maps.Marker({
                    map: mapaVisual,
                    position: { 
                        lat: parseFloat(trayecto.latitud), 
                        lng: parseFloat(trayecto.longitud) 
                    },
                    title: "Ruta: " + (trayecto.codigoRuta || "N/A"),
                    animation: google.maps.Animation.DROP // Añadimos una pequeña animación
                });

                // Opcional: InfoWindow al hacer clic
                const infoWindow = new google.maps.InfoWindow({
                    content: `<div><strong>Ruta: ${trayecto.codigoRuta}</strong><br>Lat: ${trayecto.latitud}</div>`
                });
                
                marker.addListener('click', () => {
                    infoWindow.open(mapaVisual, marker);
                });
            }
        });
    })
    .catch(error => {
        console.error('Error crítico al cargar marcadores:', error);
        alert("No se pudieron cargar los puntos en el mapa. Revisa la consola.");
    });
};