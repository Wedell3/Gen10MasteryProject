#ui 3 hours

## Menu Options Enum 15 minutes
    Create menu options with value and message fields
        [x] exit
        [x] create reservation
        [x] view by host
        [x] update reservation
        [x] delete reservation
        
##Controller 45 minutes
    [x] run method
        [x] do while loop
        [x] switch statement with cases for all menu options
        [x] calls to private methods
        
    [x] create reservation
        [x] view.displayHeader
        [x] view.makeReservation
        [x] result = reservationService.add(reservation)
        [x] view.display result
        
    [x] view reservations by host
        [x] display header
        [x] view.getHost
        [x] reservationService.getReservationsByHost(host)
        [x] view display reservations
    
    [x] update reservation
        [x] display header
        [x] view.getHost
        [x] view.getGuest
        [x] reservationService.updateReservation( host, guest)
        [x] display result
    
    [x] delete reservation
        [x] display header
        [x] view.getHost
        [x] view.getGuest
        [x] reservationService.updateReservation( host, guest)
        [x] display result            

##View 90 minutes
    displayHeader
    displayReservations
    displayReservationInfo
    makeReservation
    getHost
    getGuest
    readDate
    readString
    readRequiredString