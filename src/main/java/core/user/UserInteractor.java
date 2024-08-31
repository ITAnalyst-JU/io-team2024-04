package core.user;

import core.preferences.IInternalUserPreferencesInteractor;

public class UserInteractor {
    private final IInternalUserPreferencesInteractor internalUserPreferencesInteractor;

    public UserInteractor(IInternalUserPreferencesInteractor internalUserPreferencesInteractor) {
        this.internalUserPreferencesInteractor = internalUserPreferencesInteractor;
    }

    public void setUserName(String name) {
        internalUserPreferencesInteractor.setUserName(name);
    }

    public String getUserName() {
        return internalUserPreferencesInteractor.getUserName();
    }
}
