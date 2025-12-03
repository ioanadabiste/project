package repository.user;

public abstract class UserRepositoryDecorator implements UserRepository {
    protected UserRepository decoratorUserRepository;
    public UserRepositoryDecorator(UserRepository userRepository) {
        this.decoratorUserRepository = userRepository;
    }
}
