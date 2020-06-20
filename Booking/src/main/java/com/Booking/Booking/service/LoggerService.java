package com.Booking.Booking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.logging.*;

@Service
public class LoggerService {

    @Autowired
    SessionService sessionService;

    public void doLog(String function, String result, String nature) {
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;
        String user = sessionService.getLoggedUsername();

        try {
            File f = new File("/tmp/RentACarLogFile.log.0");
            if (f.exists() && !f.isDirectory()) {
                Path path = Paths.get("/tmp/RentACarLogFile.log.0");
                UserPrincipal authenticatedUsers = path.getFileSystem().getUserPrincipalLookupService()
                        .lookupPrincipalByName("Authenticated Users");
                AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);

                // Create ACL to give "Authenticated Users" "modify" access
                AclEntry entry = AclEntry.newBuilder().setType(AclEntryType.ALLOW).setPrincipal(authenticatedUsers)
                        .setPermissions(AclEntryPermission.LIST_DIRECTORY, AclEntryPermission.DELETE,
                                AclEntryPermission.DELETE_CHILD, AclEntryPermission.READ_DATA,
                                AclEntryPermission.READ_ATTRIBUTES, AclEntryPermission.ADD_FILE,
                                AclEntryPermission.WRITE_DATA, AclEntryPermission.WRITE_ATTRIBUTES)
                        .build();

                List<AclEntry> acl = view.getAcl();
                acl.add(0, entry); // insert before any DENY entries
                view.setAcl(acl);

                fh = new FileHandler("/tmp/RentACarLogFile.log", 1000000, 1, true);
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);

                if (nature.equals("INFO")) {
                    logger.info(" | " + user + " | " + function + " | " + result + ";");
                } else if (nature.equals("WARNING")) {
                    logger.warning(" | " + user + " | " + function + " | " + result + ";");
                } else if (nature.equals("ERROR")) {
                    logger.severe(" | " + user + " | " + function + " | " + result + ";");
                }

                fh.close();

                AclEntry entryAfter = AclEntry.newBuilder().setType(AclEntryType.DENY).setPrincipal(authenticatedUsers)
                        .setPermissions(AclEntryPermission.DELETE, AclEntryPermission.DELETE_CHILD,
                                AclEntryPermission.WRITE_DATA, AclEntryPermission.WRITE_ATTRIBUTES)
                        .build();

                List<AclEntry> aclAfter = view.getAcl();
                aclAfter.add(0, entryAfter);
                view.setAcl(aclAfter);
            } else {
                fh = new FileHandler("/tmp/Logs/RentACarLogFile.log", 1000000, 5, true);
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);

                if (nature.equals("INFO")) {
                    logger.info(" | " + user + " | " + function + " | " + result + ";");
                } else if (nature.equals("WARNING")) {
                    logger.warning(" | " + user + " | " + function + " | " + result + ";");
                } else if (nature.equals("ERROR")) {
                    logger.severe(" | " + user + " | " + function + " | " + result + ";");
                }

                Path path = Paths.get("/tmp/RentACarLogFile.log.0");
                UserPrincipal authenticatedUsers = path.getFileSystem().getUserPrincipalLookupService()
                        .lookupPrincipalByName("Authenticated Users");
                AclFileAttributeView view = Files.getFileAttributeView(path, AclFileAttributeView.class);

                // Create ACL to give "Authenticated Users" "modify" access
                AclEntry entry = AclEntry.newBuilder().setType(AclEntryType.DENY).setPrincipal(authenticatedUsers)
                        .setPermissions(AclEntryPermission.DELETE, AclEntryPermission.DELETE_CHILD,
                                AclEntryPermission.WRITE_DATA, AclEntryPermission.WRITE_ATTRIBUTES)
                        .build();

                List<AclEntry> acl = view.getAcl();
                acl.add(0, entry);
                view.setAcl(acl);

            }

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}