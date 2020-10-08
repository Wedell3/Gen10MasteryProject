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
        
    [ ] create reservation
        [ ] view.displayHeader
        [ ] view.makeReservation
        [ ] result = reservationService.add(reservation)
        [ ] view.display result
        
    [ ] view reservations by host
        [x] display header
        [x] view.getHost
        [x] reservationService.getReservationsByHost(host)
        [ ] view display reservations
    
    [ ] update reservation
        [ ] display header
        [ ] view.getHost
        [ ] view.getGuest
        [ ] reservationService.updateReservation( host, guest)
        [ ] display result
    
    [ ] delete reservation
        [ ] display header
        [ ] view.getHost
        [ ] view.getGuest
        [ ] reservationService.updateReservation( host, guest)
        [ ] display result            

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