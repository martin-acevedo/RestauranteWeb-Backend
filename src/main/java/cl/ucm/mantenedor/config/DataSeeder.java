package cl.ucm.mantenedor.config;

import cl.ucm.mantenedor.entities.Rol;
import cl.ucm.mantenedor.entities.Usuario;
import cl.ucm.mantenedor.repository.RolRepository;
import cl.ucm.mantenedor.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("====== INICIANDO DATA SEEDER ======");

        // 0. Alinear base de datos con SQL nativo
        try {
            jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
            // Asegurar que existan los roles en ID 1 y 2
            jdbcTemplate.execute("INSERT IGNORE INTO rol (id_rol, name) VALUES (1, 'ROLE_ADMIN')");
            jdbcTemplate.execute("INSERT IGNORE INTO rol (id_rol, name) VALUES (2, 'ROLE_USER')");
            // Renombrar a los nuevos estándares
            jdbcTemplate.execute("UPDATE rol SET name = 'ROLE_ADMIN' WHERE id_rol = 1");
            jdbcTemplate.execute("UPDATE rol SET name = 'ROLE_USER' WHERE id_rol = 2");
            // Eliminar duplicados
            jdbcTemplate.execute("DELETE FROM rol WHERE id_rol NOT IN (1, 2)");
            jdbcTemplate.execute("DELETE FROM user_rol WHERE rol_fk NOT IN (1, 2)");
            jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
            System.out.println("Base de datos de roles alineada exitosamente con SQL nativo.");
        } catch (Exception e) {
            System.out.println("Error al alinear roles en SQL: " + e.getMessage());
        }

        // 1. Cargar referencias
        Rol adminRol = rolRepository.findById(1).orElseThrow();
        Rol meseroRol = rolRepository.findById(2).orElseThrow();

        // 2. Inicializar usuario administrador por defecto
        Optional<Usuario> adminOpt = usuarioRepository.findById("11.111.111-1");
        if (adminOpt.isPresent()) {
            Usuario admin = adminOpt.get();
            admin.setRoles(List.of(adminRol));
            usuarioRepository.save(admin);
            System.out.println("Usuario administrador inicial actualizado con rol: ROLE_ADMIN");
        } else {
            System.out.println("Creando administrador inicial...");
            Usuario adminUser = new Usuario();
            adminUser.setRut("11.111.111-1");
            adminUser.setName("Administrador Inicial");
            adminUser.setPassword(passwordEncoder.encode("admin123"));
            adminUser.setRoles(List.of(adminRol));
            usuarioRepository.save(adminUser);
            System.out.println("Usuario administrador inicial creado con RUT: 11.111.111-1 y contraseña: admin123");
        }

        System.out.println("====== DATA SEEDER COMPLETADO ======");
    }
}
