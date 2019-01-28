package net.ukr.just_void;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/")
public class MyController {

    private Map<Integer, byte[]> archives = new HashMap<>();

    @RequestMapping("/")
    public String onIndex() {
        return "index";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingParams(MissingServletRequestParameterException ex) {
        System.out.println(ex.getMessage());
    }

    @RequestMapping(value = "/upload_files", method = RequestMethod.POST)
    public ModelAndView archiveFiles(@RequestParam MultipartFile[] file) {
        int totalSize = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zout = new ZipOutputStream(baos)) {
            for (MultipartFile i : file) {
                if (i.getSize() != 0) {
                    totalSize += i.getSize();
                    zout.putNextEntry(new ZipEntry(i.getOriginalFilename()));
                    zout.write(i.getBytes(), 0, (int) i.getSize());
                    zout.closeEntry();
                }
            }
        } catch (IOException e) {
            throw new FileErrorException();
        }
        if (totalSize== 0) {
            return new ModelAndView("index");
        }
        byte[] bytes = baos.toByteArray();
        int hash = bytes.hashCode();
        archives.put(hash, bytes);
        return new ModelAndView("archive", "archive_hash", hash);
    }

    @RequestMapping("/get-archive/{hash}")
    public ResponseEntity<byte[]> getArchive(@PathVariable int hash) {
        if (!archives.containsKey(hash)) {
            throw new FileNotFoundException();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "archive.zip");
        return new ResponseEntity<>(archives.get(hash), headers, HttpStatus.OK);
    }
}
