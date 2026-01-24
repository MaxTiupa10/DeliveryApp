import React from "react";
import { Link as RouterLink } from 'react-router-dom';
import { Link as MuiLink } from '@mui/material';
import {Avatar, Box, Button, Container, Grid, Paper, TextField, Typography} from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";

function Login() {


    return (
        <Container maxWidth="xs">
            <Paper variant="outlined">

                <Box sx={{ display: "flex", alignItems: "center" , flexDirection: "column" , p: 4 }}>
                    <Avatar>
                        <LockOutlinedIcon > </LockOutlinedIcon>
                    </Avatar>
                    <Typography component="h1" variant="h5" p={1}>
                        Вхід
                    </Typography>
                    <TextField
                        label="Email"
                        type="email"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                    />
                    <TextField
                        label="Password"
                        type="password"
                        variant="outlined"
                        fullWidth
                        margin="normal"
                    />
                    <Button type="submit" color="primary" fullWidth variant="contained" sx={{mt:3, mb:2}}>
                        Увійти
                    </Button>
                    <Grid>
                        <MuiLink component={RouterLink} to="/register">
                            Немає акаунту? Зареєструватися.
                        </MuiLink>
                    </Grid>
                </Box>
            </Paper>
        </Container>
    )
}
export default  Login;