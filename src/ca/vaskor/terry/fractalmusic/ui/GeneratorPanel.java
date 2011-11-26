package ca.vaskor.terry.fractalmusic.ui;

/**
 * An abstract class that combines the {@link NoteGeneratorReturner} and
 * {@link DataRetriever} interfaces, built upon {@link RecursiveEnableJPanel}
 * so it can be enabled and disabled recursively.
 * 
 * @author Terry Vaskor
 */
public abstract class GeneratorPanel<E>
extends RecursiveEnableJPanel 
implements NoteGeneratorReturner, DataRetriever<E>
{}
