package com.devut.proyecto.services;

import com.devut.proyecto.entities.Documento;
import com.devut.proyecto.entities.Vehiculo;
import com.devut.proyecto.entities.VehiculoDocumento;
import com.devut.proyecto.repository.DocumentoRepository;
import com.devut.proyecto.repository.VehiculoRepository;
import com.devut.proyecto.services.interfaces.IVehiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServiceImpl implements IVehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    private static final String REGEX_AUTO = "^[A-Za-z]{3}[0-9]{3}$";
    private static final String REGEX_MOTO = "^[A-Za-z]{3}[0-9]{2}[A-Za-z]{1}$";

    private void validarPlaca(Vehiculo vehiculo) {
        String placa = vehiculo.getPlaca();
        String tipo  = vehiculo.getTipoVehiculo();

        if ("Automóvil".equals(tipo) && !placa.matches(REGEX_AUTO)) {
            throw new IllegalArgumentException(
                "Placa inválida para Automóvil. Formato correcto: ABC123 (EJEMPLO: RNX330)");
        }
        if ("Motocicleta".equals(tipo) && !placa.matches(REGEX_MOTO)) {
            throw new IllegalArgumentException(
                "Placa inválida para Motocicleta. Formato correcto: ABC12D (EJEMPLO: BOV18D)");
        }
    }

    @Override
    public Vehiculo save(Vehiculo vehiculo) {
        if (vehiculo.getDocumentos() == null || vehiculo.getDocumentos().isEmpty()) {
            throw new IllegalArgumentException(
                "No se puede crear un vehículo sin documentos asociados.");
        }
        validarPlaca(vehiculo);
        vehiculo.getDocumentos().forEach(doc -> {
            doc.setEstado("En Verificación");
            doc.setVehiculo(vehiculo);
        });
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    @Override
    public Optional<Vehiculo> findById(Integer id) {
        return vehiculoRepository.findById(id);
    }

    @Override
    public Vehiculo update(Integer id, Vehiculo datos) {
        Vehiculo existente = vehiculoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con id: " + id));
        validarPlaca(datos);

        existente.setTipoVehiculo(datos.getTipoVehiculo());
        existente.setPlaca(datos.getPlaca());
        existente.setTipoServicio(datos.getTipoServicio());
        existente.setTipoCombustible(datos.getTipoCombustible());
        existente.setCapacidadPasajeros(datos.getCapacidadPasajeros());
        existente.setColor(datos.getColor());
        existente.setModelo(datos.getModelo());
        existente.setMarca(datos.getMarca());
        existente.setLinea(datos.getLinea());

        return vehiculoRepository.save(existente);
    }

    @Override
    public void delete(Integer id) {
        vehiculoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con id: " + id));
        vehiculoRepository.deleteById(id);
    }

    @Override
    public Optional<Vehiculo> findByPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa);
    }

    @Override
    public List<Vehiculo> findByTipoVehiculo(String tipoVehiculo) {
        return vehiculoRepository.findByTipoVehiculo(tipoVehiculo);
    }

    @Override
    public List<Vehiculo> findByTipoDocumento(String codigoDocumento) {
        return vehiculoRepository.findByCodigoDocumento(codigoDocumento);
    }

    @Override
    public List<Vehiculo> findByEstadoDocumento(String estado) {
        return vehiculoRepository.findByEstadoDocumento(estado);
    }

    @Override
    public void agregarDocumentoAVehiculo(Integer vehiculoId, Integer documentoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
            .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con id: " + vehiculoId));

        Documento documento = documentoRepository.findById(documentoId)
            .orElseThrow(() -> new RuntimeException("Documento no encontrado con id: " + documentoId));

        VehiculoDocumento vd = new VehiculoDocumento();
        vd.setVehiculo(vehiculo);
        vd.setDocumento(documento);
        vd.setEstado("En Verificación");
        vd.setFechaExpedicion(LocalDate.now());
        vd.setFechaVencimiento(LocalDate.now().plusYears(1));
        vehiculo.getDocumentos().add(vd);
        vehiculoRepository.save(vehiculo);
    }
    
    @Override
    public Optional<Vehiculo> findByPlacaConDetalles(String placa) {
        return Optional.of(vehiculoRepository.findByPlacaConDetalles(placa)
                .orElseThrow(() -> new RuntimeException(
                        "Vehículo no encontrado con placa: " + placa)));
    }

    @Override
    public List<Vehiculo> findVehiculosConDocumentosVencidos() {
        List<Vehiculo> lista = vehiculoRepository.findVehiculosConDocumentosVencidos();
        if (lista.isEmpty()) {
            throw new RuntimeException(
                    "No se encontraron vehículos con documentos vencidos.");
        }
        return lista;
    }

    @Override
    public List<Vehiculo> findVehiculosConDocumentosPorVencer(LocalDate fechaLimite) {
        List<Vehiculo> lista = vehiculoRepository
                .findVehiculosConDocumentosPorVencer(fechaLimite);
        if (lista.isEmpty()) {
            throw new RuntimeException(
                    "No se encontraron vehículos con documentos por vencer.");
        }
        return lista;
    }
}
