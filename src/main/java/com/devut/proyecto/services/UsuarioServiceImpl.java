package com.devut.proyecto.services;

import com.devut.proyecto.entities.Usuario;
import com.devut.proyecto.repository.UsuarioRepository;
import com.devut.proyecto.services.interfaces.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("UsuarioService")
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void cambiarPassword(String login, String nuevaPassword) {
        
        nuevaPassword = nuevaPassword.replace("\"", "").trim();
        
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con login: " + login));
        usuario.setPassword(nuevaPassword);
        usuarioRepository.save(usuario);
    }

    @Override
    public String regenerarApikey(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException(
                        "Usuario no encontrado con login: " + login));
        String nuevoApikey = UUID.randomUUID().toString();
        usuario.setApikey(nuevoApikey);
        usuarioRepository.save(usuario);
        return nuevoApikey;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        System.out.println("Buscar el usuario con el repositorio y si no existe lanzar una excepcion.");

        Usuario appUser = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con login: " + username));

        List<GrantedAuthority> grantList = new ArrayList<>();
        // Este objeto es usado para manejar roles de usuario, en este caso NO APLICA.

        System.out.println("Crear El objeto UserDetails que va a ir en sesion y retornarlo.");
        UserDetails user = new User(
                appUser.getId().getLogin(),
                appUser.getPassword(),
                grantList);

        System.out.println("user: [" + user + "]");
        return user;
    }
}
