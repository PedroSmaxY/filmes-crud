            package com.facul.filmes.services;

            import com.facul.filmes.domain.entities.Rental;
            import com.facul.filmes.domain.enums.RentalStatus;
            import com.facul.filmes.repositories.CustomerRepository;
            import com.facul.filmes.repositories.EmployeeRepository;
            import com.facul.filmes.repositories.RentalRepository;
            import com.facul.filmes.services.exceptions.BadRequestException;
            import org.springframework.stereotype.Service;
            import org.springframework.transaction.annotation.Transactional;

            import java.time.LocalDate;

            @Service
            public class RentalService {
                private final MovieService movieService;
                private final CustomerRepository customerRepository;
                private final RentalRepository rentalRepository;
                private final EmployeeRepository employeeRepository;

                public RentalService(MovieService movieService,
                                     CustomerRepository customerRepository,
                                     EmployeeRepository employeeRepository,
                                     RentalRepository rentalRepository) {
                    this.rentalRepository = rentalRepository;
                    this.movieService = movieService;
                    this.customerRepository = customerRepository;
                    this.employeeRepository = employeeRepository;
                }

                @Transactional
                public Rental createRental(Long movieId, Long customerId, Long employeeId) {
                    if (movieId == null || customerId == null || employeeId == null) {
                        throw new BadRequestException("Movie and customer are required.");
                    }

                    var customer = customerRepository.findById(customerId)
                            .orElseThrow(() -> new BadRequestException("Customer not found."));
                    var employee = employeeRepository.findById(employeeId)
                            .orElseThrow(() -> new BadRequestException("Employee not found."));
                    var movie = movieService.findMovieById(movieId);

                    movieService.decreaseAvailableCopies(movieId, 1);

                    var rental = new Rental();
                    rental.setCustomer(customer);
                    rental.setProcessedBy(employee);
                    rental.setMovie(movie);
                    rental.setRentalDate(LocalDate.now());
                    rental.setStatus(RentalStatus.OPEN);

                    return rentalRepository.save(rental);
                }

                @Transactional
                public Rental returnRental(Long rentalId) {
                    var rental = rentalRepository.findById(rentalId)
                            .orElseThrow(() -> new BadRequestException("Rental not found."));

                    if (rental.getReturnDate() != null) {
                        throw new BadRequestException("Rental already returned.");
                    }

                    movieService.increaseAvailableCopies(rental.getMovie().getId(), 1);

                    var today = LocalDate.now();
                    rental.setReturnDate(today);
                    rental.setStatus(RentalStatus.RETURNED);

                    return rentalRepository.save(rental);
                }
            }