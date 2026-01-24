import { Outlet, Link } from "react-router-dom";
import {
    Container,
    Box,
    AppBar,
    Toolbar,
    Typography,
    Button,
    Stack,
    CssBaseline,
    ThemeProvider,
    createTheme
} from "@mui/material";
import DeliveryDiningIcon from '@mui/icons-material/DeliveryDining';
const theme = createTheme({
    palette: {
        mode: 'dark',
        primary: { main: '#1976d2' },
        background: { default: '#121212' },
    },
});

function MainLayout() {
    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh' }}>

                <AppBar position="sticky" color="primary" elevation={3}>
                    <Container maxWidth="lg">
                        <Toolbar disableGutters>


                            <Box
                                component={Link}
                                to="/"
                                sx={{
                                    display: 'flex',
                                    alignItems: 'center',
                                    textDecoration: 'none',
                                    color: 'inherit',
                                    flexGrow: 1
                                }}
                            >
                                {/* Іконка з відступом праворуч */}
                                <DeliveryDiningIcon sx={{ mr: 1.5, fontSize: '2rem' }} />

                                <Typography variant="h6" sx={{ fontWeight: 'bold', letterSpacing: '1px' }}>
                                    DELIVERY APP
                                </Typography>
                            </Box>

                            <Stack direction="row" spacing={2}>
                                <Button variant="contained" color="inherit" component={Link} to="/">Головна</Button>
                                <Button variant="contained" color="inherit" component={Link} to="/login">Вхід</Button>
                                <Button variant="contained" color="inherit" component={Link} to="/register">
                                    Реєстрація
                                </Button>
                            </Stack>

                        </Toolbar>
                    </Container>
                </AppBar>

                {/* Контент сайту (обмежений по центру для читабельності) */}
                <Box component="main" sx={{ flex: 1, py: 4 }}>
                    <Container maxWidth="lg">
                        <Outlet />
                    </Container>
                </Box>


                <Box
                    component="footer"
                    sx={{
                        py: 3,
                        backgroundColor: 'background.paper',
                        width: '100%',
                        borderTop: '1px solid #333'
                    }}
                >
                    <Container maxWidth="lg">
                        <Typography variant="body2" color="text.secondary" align="right">
                            © 2026 Delivery App. Content was created by Lushpak.
                        </Typography>
                    </Container>
                </Box>

            </Box>
        </ThemeProvider>
    );
}

export default MainLayout;