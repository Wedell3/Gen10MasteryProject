#ui 3 hours

## Menu Options Enum 15 minutes
    Create menu options with value and message fields
        [ ] exit
        [ ] create reservation
        [ ] view by host
        [ ] update reservation
        [ ] delete reservation
        
##Controller 45 minutes
    [ ] run method
        [ ] do while loop
        [ ] switch statement with cases for all menu options
        [ ] calls to private methods
        
    [ ] create reservation
        [ ] view.displayHeader
        [ ] view.makeReservation
        [ ] result = reservationService.add(reservation)
        [ ] view.display result
        
    [ ] view reservations by host
        [ ] display header
        [ ] view.getHost
        [ ] reservationService.getReservationsByHost(host)
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

##ConsoleIO 30 minutes
    readDate
    readString
    readRequiredString