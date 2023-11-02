package org.example;

import java.util.Collection;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class GaRequest {
    public String clientId;
    public Collection<Event> events;
}

@Data
@AllArgsConstructor(staticName = "of")
class Event {
    public String name;
    public Map<String, Object> params;
}