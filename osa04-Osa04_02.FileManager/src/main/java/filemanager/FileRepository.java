/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Henri
 */
public interface FileRepository  extends JpaRepository<FileObject, Long> {
//
}
        
