#Models 45 minutes total

##Host 15 minutes
    Constructor
    Empty
    firstName, lastName, email

    Fields
    [ ] String firstName
    [ ] String lastName
    [ ] String email
    [ ] String id (GUID generated)
    [ ] BigDecimal weekdayRate
    [ ] BigDecimal weekendRate
    
    Getters and Setters
    [ ] firstName
    [ ] lastName
    [ ] email
    [ ] id
    [ ] weekdayRate
    [ ] weekendRate

##Guest 15 minutes
    Constructors
    [x] Empty
    [x] firstName, lastName, email
    
    Fields
    [x] String firstName
    [x] String lastName
    [x] String email
    
    Getters and Setters
    [x] firstName
    [x] lastName
    [x] email
    
##Reservation 15 minutes
    Constructors
    Empty
    
    Fields
    [ ] Host host
    [ ] Guest guest
    [ ] LocalDate startDate
    [ ] LocalDate endDate
    [ ] BigDecimal cost
    [ ] int id
    
    Getters and Setters
    [ ] host
    [ ] guest
    [ ] startDate
    [ ] endDate
    [ ] cost
    [ ] id
