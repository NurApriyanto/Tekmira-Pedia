package org.alfredo.tekmirapedia.model;

import java.util.ArrayList;

public interface EnsiklopediaCallback {
    void preExecute();
    void postExecute(ArrayList<Ensiklopedia> ensiklopedias);
}
