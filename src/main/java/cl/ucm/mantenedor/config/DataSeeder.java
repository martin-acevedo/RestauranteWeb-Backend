package cl.ucm.mantenedor.config;

import cl.ucm.mantenedor.entities.Rol;
import cl.ucm.mantenedor.entities.Usuario;
import cl.ucm.mantenedor.repository.RolRepository;
import cl.ucm.mantenedor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("====== INICIANDO DATA SEEDER ======");

        // 1. Inicializar roles si no existen
        Rol adminRol = getOrCreateRol("Administrador");
        Rol meseroRol = getOrCreateRol("Mesero");

        // 2. Inicializar usuario administrador por defecto si la BD de usuarios está vacía
        if (usuarioRepository.count() == 0) {
            System.out.println("La base de datos de usuarios está vacía. Creando administrador inicial...");
            Usuario adminUser = new Usuario();
            adminUser.setRut("11.111.111-1");
            adminUser.setName("Administrador Inicial");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRoles(List.of(adminRol));
            usuarioRepository.save(adminUser);
            System.out.println("Usuario administrador inicial creado con RUT: 11.111.111-1 y contraseña: admin123");
        } else {
            System.out.println("Ya existen usuarios registrados en la base de datos.");
        }

        System.out.println("====== DATA SEEDER COMPLETADO ======");
    }

    private Rol getOrCreateRol(String roleName) {
        Optional<Rol> rolOpt = rolRepository.findByName(roleName);
        if (rolOpt.isEmpty()) {
            System.out.println("Rol '" + roleName + "' no encontrado. Creándolo...");
            Rol newRol = new Rol();
            newRol.setName(roleName);
            return rolRepository.save(newRol);
        } else {
            System.out.println("Rol '" + roleName + "' ya existe.");
            return rolOpt.get();
        }
    }
}
