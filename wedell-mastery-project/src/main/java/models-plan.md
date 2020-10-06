#Models 45 minutes total

##Host 15 minutes
    Constructor
    [x] Empty
    [x] firstName, lastName, email

    Fields
    [x] String firstName
    [x] String lastName
    [x] String email
    [x] String id (GUID generated)
    [x] BigDecimal weekdayRate
    [x] BigDecimal weekendRate
    
    Getters and Setters
    [x] firstName
    [x] lastName
    [x] email
    [x] id
    [x] weekdayRate
    [x] weekendRate

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
    [x] Empty
    
    Fields
    [x] Host host
    [x] Guest guest
    [x] LocalDate startDate
    [x] LocalDate endDate
    [x] BigDecimal cost
    [x] int id
    
    Getters and Setters
    [x] host
    [x] guest
    [x] startDate
    [x] endDate
    [x] cost
    [x] id
