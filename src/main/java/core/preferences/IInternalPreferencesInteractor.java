package core.preferences;

// NOTE: I believe interface segregation principle
public interface IInternalPreferencesInteractor extends IInternalAudioPreferencesInteractor, IInternalWindowPreferencesInteractor, IInternalUserPreferencesInteractor { }
