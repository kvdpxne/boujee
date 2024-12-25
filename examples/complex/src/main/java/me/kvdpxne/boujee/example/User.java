package me.kvdpxne.boujee.example;

import java.io.Serializable;
import java.util.UUID;
import me.kvdpxne.boujee.locale.LocaleSourceProvider;

public interface User
  extends LocaleSourceProvider, Serializable {

  UUID getUid();

  String getFirstName();

  String getLastName();
}
