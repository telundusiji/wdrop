create table IF NOT EXISTS wdrop_plugin (
  plugin_id VARCHAR(48),
  plugin_name VARCHAR(50) not null,
  class_name VARCHAR(2048),
  url VARCHAR(2048),
  lib_path VARCHAR(2048),
  context_path VARCHAR(2048)
)
WITH (OIDS = FALSE)