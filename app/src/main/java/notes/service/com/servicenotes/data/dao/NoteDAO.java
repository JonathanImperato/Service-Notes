package notes.service.com.servicenotes.data.dao;

import com.google.inject.ImplementedBy;

import java.util.List;

import notes.service.com.servicenotes.data.Note;
import notes.service.com.servicenotes.data.dao.impl.sqlite.NoteSQLiteDAO;

/**
 * Interfaz que deben implementar todas las clases que sean fuente de datos de notas.
 *
 * @author Daniel Pedraza Arcega
 */
@ImplementedBy(NoteSQLiteDAO.class)
public interface NoteDAO {

    /** @return todas las notas de la fuente de datos*/
    List<Note> fetchAll();

    /**
     * Inserta una nota en la fuente de datos.
     *
     * @param note la nota a insertar.
     */
    void insert(Note note);

    /**
     * Actualiza una nota en la fuente de datos.
     *
     * @param note la nota a actualizar.
     */
    void update(Note note);

    /**
     * Borra una nota en la fuente de datos.
     *
     * @param note la nota a borrar.
     */
    void delete(Note note);
}