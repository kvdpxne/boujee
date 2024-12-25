package me.kvdpxne.boujee.example;

import java.util.UUID;
import me.kvdpxne.boujee.locale.LocaleSource;

public class BasicUser
  implements User {

  private static final long serialVersionUID = -7663416034273148404L;

  private final UUID uid;
  private String firstName;
  private String lastName;
  private LocaleSource localeSource;

  public BasicUser(
    final UUID uid,
    final String firstName,
    final String lastName,
    final LocaleSource localeSource
  ) {
    this.uid = uid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.localeSource = localeSource;
  }

  @Override
  public UUID getUid() {
    return this.uid;
  }

  @Override
  public String getFirstName() {
    return this.firstName;
  }

  @Override
  public String getLastName() {
    return this.lastName;
  }

  @Override
  public LocaleSource getLocaleSource() {
    return this.localeSource;
  }
}
