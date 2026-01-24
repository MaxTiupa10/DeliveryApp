const VITE_MAPBOX_ACCESS_TOKEN = import.meta.env.VITE_MAPBOX_ACCESS_TOKEN;
import React , {useState} from "react";
import {Avatar, Box, Button, Container, Paper, TextField, Typography} from "@mui/material";
import LockOpenIcon from '@mui/icons-material/LockOpen';
import {MuiTelInput, matchIsValidTel} from "mui-tel-input";
import CityAutocomplete from "../components/CityAutocomplete";

function Register() {
    const [password , setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [phoneNumber,setPhoneNumber] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [address, setAddress] = useState('');
    const [isPhoneError, setIsPhoneError] = useState(false);
    const [email, setEmail] = useState('');
    const [isEmailError, setIsEmailError] = useState(false);
    const [lengthError, setLengthError] = useState(false);
    const [passwordStrength, setPasswordStrength] = useState(null);
    const [error , setError] = useState(false);


     const handleNameChange = (e) => {
         e.preventDefault();
         const newName = e.target.value;
         setFirstName(newName);
     }

    const handleSurnameChange = (e) => {
        e.preventDefault();
        const newSurname = e.target.value;
        setLastName(newSurname);
    }

     const handleInputChange = (e) =>{
        e.preventDefault();
        const newPassword = e.target.value;
        setPassword(newPassword);
        checkPasswordStrength(newPassword);
     };

     const handleConfirmPasswordChange = (e) =>{
         e.preventDefault();
         const newConfirmedPassword = e.target.value;
         setConfirmPassword(newConfirmedPassword);
         checkPasswordStrength(newConfirmedPassword);
         checkPasswordError(newConfirmedPassword);
     };

     const handlePhoneNumberChange = (value) =>{
         setPhoneNumber(value);
         checkPhoneError(value);
     }

     const checkPasswordError = (newConfirmedPassword) =>{
        setError(newConfirmedPassword !== password);

     }
    const checkPhoneError = (newPhoneNumber) =>{
         setPhoneNumber(newPhoneNumber);
         setIsPhoneError(!matchIsValidTel(newPhoneNumber));
    }

    const checkPasswordStrength = (newPassword) => {
        const minLength = 8;
        setLengthError(newPassword.length < minLength);

        const isLengthValid = newPassword.length >= minLength;
        const hasLetters = /[a-z]/.test(newPassword);
        const hasUpperCase = /[A-Z]/.test(newPassword);
        const hasNumber = /\d/.test(newPassword);
        const hasSpecialChars = /[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]/.test(newPassword);

        const strength =
            isLengthValid + hasLetters + hasUpperCase + hasNumber + hasSpecialChars;

        setPasswordStrength(strength);
    }

    const getStrengthColor = () => {
        if(lengthError) {
            return "red";
        } else if (passwordStrength === null){
            return "";
        }else if (passwordStrength <= 1){
            return "red";
        }else if (passwordStrength <= 2){
            return "yellow";
        }else if (passwordStrength <= 3){
            return "orange";
        }else if (passwordStrength <= 4){
            return "grey";
        }
        else if (passwordStrength <= 5){
            return "green";
        }
    }

    const handleEmailChange = (e) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
         e.preventDefault();
         const newEmail = e.target.value;
         setEmail(newEmail);
        setIsEmailError(!emailRegex.test(newEmail));
    }


    const handleSubmit = (e) => {
        e.preventDefault();
        if(!isEmailError && !isPhoneError && !error && !lengthError && email !== '' && password !== ''){
            const UserData = {
                email: email,
                password: password,
                firstName: firstName,
                lastName: lastName,
                phoneNumber: phoneNumber,
                address: address
            }
        }
    }


    return (
        <Container maxWidth="xs">
            <Paper variant="outlined">
                <Box sx={{display: 'flex', flexDirection: 'column', alignItems: 'center', p:4}}>
                        <Avatar>
                            <LockOpenIcon></LockOpenIcon>
                        </Avatar>
                    <Typography component="h1" variant="h5" p={1}>
                        Реєстрація
                    </Typography>
                    <TextField
                        label={"First Name"}
                        variant="outlined"
                        margin="normal"
                        fullWidth
                        type="text"
                        value={firstName}
                        onChange={handleNameChange}
                    />
                    <TextField
                        label={"Last Name"}
                        variant="outlined"
                        margin="normal"
                        fullWidth
                        type="text"
                        value={lastName}
                        onChange={handleSurnameChange}
                    />
                    <CityAutocomplete
                        value={address}
                        onChange={(value) => {
                            setAddress(value);
                            value.label
                            value.region
                            value.coordinates

                        }}
                    />

                    <MuiTelInput
                        label={"Phone number"}
                        type="tel"
                        margin="normal"
                        fullWidth
                        variant="outlined"
                        value={phoneNumber}
                        onChange = {handlePhoneNumberChange}
                        defaultCountry="UA"
                        error={isPhoneError}
                        helperText={isPhoneError ? "Некоректний номер телефону" : ""}
                    />
                    <TextField
                        label="Email"
                        type="email"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        value={email}
                        onChange = {handleEmailChange}
                        error={isEmailError}
                        helperText={isEmailError? "Невірний email":""}
                    />
                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        value={password}
                        onChange={handleInputChange}
                    />
                    <Box sx={{height: '4px', width: '100%',marginTop: '8px',backgroundColor: getStrengthColor(),transition: 'all 0.3s'}}>
                    </Box>
                    <TextField
                        label="Confirm password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                        value={confirmPassword}
                        onChange={handleConfirmPasswordChange}
                        error={error}
                        helperText={error? "Паролі не збігаються": ""}
                    />
                    <Button type="submit" color="primary" fullWidth variant="contained" sx={{mt:3, mb:2}}>
                    Зареєструватись
                    </Button>
                </Box>
            </Paper>
        </Container>
    )
}
export default Register;